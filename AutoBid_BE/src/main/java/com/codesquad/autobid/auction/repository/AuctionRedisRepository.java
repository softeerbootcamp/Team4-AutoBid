package com.codesquad.autobid.auction.repository;

import com.codesquad.autobid.auction.repository.exceptions.BidSaveFailedException;
import com.codesquad.autobid.bid.domain.Bid;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class AuctionRedisRepository {

    private static final Integer LOCKING_TIME = 5;
    private static final Integer LEASE_TIME = 1;
    private static final Integer REDIS_SCAN_FROM = 0;
    private static final Integer REDIS_SCAN_TO = -1;
    private final RedisTemplate redisTemplate;
    private final RedissonClient redissonClient;
    private static ValueOperations stringOps;
    private static ZSetOperations zSetOps;

    public AuctionRedisRepository(RedisTemplate redisTemplate, RedissonClient redissonClient) {
        this.redisTemplate = redisTemplate;
        this.redissonClient = redissonClient;
        stringOps = redisTemplate.opsForValue();
        zSetOps = redisTemplate.opsForZSet();
    }

    public boolean saveBid(Bid bid) {
        Long auctionId = bid.getAuctionId().getId();
        Map<AuctionRedisKey, String> keys = AuctionRedisKey.generate(auctionId);
        RLock rLock = redissonClient.getLock(keys.get(AuctionRedisKey.LOCK));
        try {
            boolean hasAcquiredLock = rLock.tryLock(LOCKING_TIME, LEASE_TIME, TimeUnit.SECONDS);
            if (!hasAcquiredLock) {
                log.error("lock failed : {}", bid);
                throw new BidSaveFailedException();
            }
            Long curPrice = getPrice(auctionId);
            if (bid.getPrice() > curPrice) {
                setPrice(auctionId, bid.getPrice());
                tryUnlock(rLock);
                log.info("낙찰 성공 : {}", bid);
                return true;
            }
        } catch (InterruptedException e) {
            throw new BidSaveFailedException();
        }
        tryUnlock(rLock);
        return false;
    }

    private void tryUnlock(RLock rLock) {
        if (rLock.isLocked()) {
            rLock.unlock();
        }
    }

    public void save(AuctionRedisDTO auctionRedisDTO) {
        Map<AuctionRedisKey, String> keys = AuctionRedisKey.generate(auctionRedisDTO.getAuctionId());
        setPrice(auctionRedisDTO.getAuctionId(), auctionRedisDTO.getPrice());
        if (auctionRedisDTO.hasBidder()) {
            saveBidders(keys.get(AuctionRedisKey.BIDDERS), auctionRedisDTO.getAuctionRedisBidderDto());
        }
    }

    private void saveBidders(String key, List<AuctionRedisBidderDTO> auctionRedisBidderDTOS) {
        zSetOps.add(
                key,
                auctionRedisBidderDTOS.stream()
                        .map(bidder -> ZSetOperations.TypedTuple.of(bidder.getUserId(), (double) -1 * bidder.getPrice()))
                        .collect(Collectors.toSet()));
    }

    public void deleteAuction(Long auctionId) {
        Map<AuctionRedisKey, String> keys = AuctionRedisKey.generate(auctionId);
        for (String key : keys.values()) {
            redisTemplate.delete(key);
        }
    }

    public AuctionRedisDTO findById(Long auctionId) {
        try {
            Map<AuctionRedisKey, String> keys = AuctionRedisKey.generate(auctionId);
            Long price = Integer.toUnsignedLong((int) stringOps.get(keys.get(AuctionRedisKey.PRICE)));
            List<AuctionRedisBidderDTO> auctionRedisBidderDTOS = parseToBidderSet(keys.get(AuctionRedisKey.BIDDERS), REDIS_SCAN_FROM, REDIS_SCAN_TO);
            return AuctionRedisDTO.of(auctionId, price, auctionRedisBidderDTOS);
        } catch (NullPointerException e) {
            return null;
        }
    }

    private List<AuctionRedisBidderDTO> parseToBidderSet(String key, int from, int to) {
        Set<DefaultTypedTuple> set = zSetOps.rangeWithScores(key, from, to);
        return set.stream()
                .map((dtt) -> AuctionRedisBidderDTO.of(
                                Integer.toUnsignedLong((int) dtt.getValue()),
                                -1 * dtt.getScore().longValue()
                        )
                )
                .collect(Collectors.toList());
    }

    private Long getPrice(Long auctionId) {
        return Long.valueOf(String.valueOf(stringOps.get(AuctionRedisKey.generate(auctionId).get(AuctionRedisKey.PRICE))));
    }

    private void setPrice(Long auctionId, Long price) {
        stringOps.set(AuctionRedisKey.generate(auctionId).get(AuctionRedisKey.PRICE), price);
    }

    public void deleteBidder(Long auctionId, Long userId) {
        String auctionBidderKey = AuctionRedisKey.generate(auctionId).get(AuctionRedisKey.BIDDERS);
        zSetOps.remove(auctionBidderKey, userId);
    }
}

package com.codesquad.autobid.auction.repository;

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
        Map<AuctionRedisKey, String> keys = AuctionRedisKey.generate(bid.getAuctionId().getId());
        RLock rLock = redissonClient.getLock(keys.get(AuctionRedisKey.LOCK));
        try {
            boolean isLocked = rLock.tryLock(LOCKING_TIME, LEASE_TIME, TimeUnit.SECONDS);
            if (!isLocked) {
                log.error("lock failed : {}", bid);
                return false;
            }
            Long curPrice = Long.valueOf(String.valueOf(stringOps.get(keys.get(AuctionRedisKey.PRICE))));
            if (bid.getPrice() > curPrice) {
                log.info(bid.getPrice() + " " + curPrice);
                stringOps.set(keys.get(AuctionRedisKey.PRICE), String.valueOf(bid.getPrice()));
                if (rLock != null && rLock.isLocked()) {
                    rLock.unlock();
                }
                log.info("낙찰 성공 : {}", bid);
                return true;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (rLock != null && rLock.isLocked()) {
            rLock.unlock();
        }
        return false;
    }

    public void save(AuctionRedisDTO auctionRedisDTO) {
        Map<AuctionRedisKey, String> keys = AuctionRedisKey.generate(auctionRedisDTO.getAuctionId());

        stringOps.set(keys.get(AuctionRedisKey.PRICE), auctionRedisDTO.getPrice());
        if (auctionRedisDTO.getAuctionRedisBidderDto().size() != 0) {
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

    public void delete(Long auctionId) {
        Map<AuctionRedisKey, String> keys = AuctionRedisKey.generate(auctionId);
        for (String key : keys.values()) {
            redisTemplate.delete(key);
        }
    }

    public AuctionRedisDTO findById(Long auctionId) {
        try {
            Map<AuctionRedisKey, String> keys = AuctionRedisKey.generate(auctionId);
            Long price = Integer.toUnsignedLong((int) stringOps.get(keys.get(AuctionRedisKey.PRICE)));
            List<AuctionRedisBidderDTO> auctionRedisBidderDTOS = parseToBidderSet(keys.get(AuctionRedisKey.BIDDERS), 0, -1);
            return AuctionRedisDTO.of(auctionId, price, auctionRedisBidderDTOS);
        } catch (NullPointerException e) {
            return null;
        }
    }

    private List<AuctionRedisBidderDTO> parseToBidderSet(String key, int from, int to) {
        Set<DefaultTypedTuple> set = zSetOps.rangeWithScores(key, from, to);
        return set.stream()
            .map((dtt) -> AuctionRedisBidderDTO.of(Integer.toUnsignedLong((int) dtt.getValue()),
                -1 * dtt.getScore().longValue()))
            .collect(Collectors.toList());
    }

    public Long getPrice(Long auctionId) {
        Map<AuctionRedisKey, String> keys = AuctionRedisKey.generate(auctionId);
        return (Long) stringOps.get(keys.get(AuctionRedisKey.PRICE));
    }
}

package com.codesquad.autobid.auction.repository;

import com.codesquad.autobid.auction.domain.Auction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class AuctionRedisRepository {

    private final RedisTemplate redisTemplate;
    private static ValueOperations stringOps;
    private static ZSetOperations zSetOps;

    public AuctionRedisRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        stringOps = redisTemplate.opsForValue();
        zSetOps = redisTemplate.opsForZSet();
    }

    public void save(AuctionRedisDTO auctionRedisDTO) {
        Map<AuctionRedisKey, String> keys = AuctionRedisUtil.generateKeys(auctionRedisDTO.getAuctionId());

        stringOps.set(keys.get(AuctionRedisKey.PRICE), auctionRedisDTO.getPrice());
        if (auctionRedisDTO.getAuctionRedisBidderDto().size() != 0) {
            saveBidders(keys.get(AuctionRedisKey.BIDDERS), auctionRedisDTO.getAuctionRedisBidderDto());
        }
    }

    private void saveBidders(String key, Set<AuctionRedisBidderDTO> auctionRedisBidderDTOS) {
        zSetOps.add(
            key,
            auctionRedisBidderDTOS.stream()
                .map(bidder -> ZSetOperations.TypedTuple.of(bidder.getUserId(), (double) -1 * bidder.getPrice()))
                .collect(Collectors.toSet()));
    }

    public void delete(Long auctionId) {
        Map<AuctionRedisKey, String> keys = AuctionRedisUtil.generateKeys(auctionId);
        for (String key : keys.values()) {
            redisTemplate.delete(key);
        }
    }

    public AuctionRedisDTO findById(Long auctionId) {
        Map<AuctionRedisKey, String> keys = AuctionRedisUtil.generateKeys(auctionId);
        Long price = Integer.toUnsignedLong((int) stringOps.get(keys.get(AuctionRedisKey.PRICE)));
        Set<AuctionRedisBidderDTO> auctionRedisBidderDTOS = parseToBidderSet(keys.get(AuctionRedisKey.BIDDERS), 0, -1);
        return AuctionRedisDTO.of(auctionId, price, auctionRedisBidderDTOS);
    }

    private Set<AuctionRedisBidderDTO> parseToBidderSet(String key, int from, int to) {
        Set<DefaultTypedTuple> set = zSetOps.rangeWithScores(key, from, to);
        return set.stream()
            .map((dtt) -> AuctionRedisBidderDTO.of(Integer.toUnsignedLong((int) dtt.getValue()), -1 * dtt.getScore().longValue()))
            .collect(Collectors.toSet());
    }

    public Long getPrice(Long auctionId) {
        Map<AuctionRedisKey, String> keys = AuctionRedisUtil.generateKeys(auctionId);
        return (Long) stringOps.get(keys.get(AuctionRedisKey.PRICE));
    }
}

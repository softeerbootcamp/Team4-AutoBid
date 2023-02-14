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

    public void save(AuctionRedis auctionRedis) {
        Map<AuctionRedisKey, String> keys = AuctionRedisUtil.generateKeys(auctionRedis.getId());

        stringOps.set(keys.get(AuctionRedisKey.PRICE), auctionRedis.getPrice());
        stringOps.set(keys.get(AuctionRedisKey.NUMBER_OF_USERS), auctionRedis.getNumberOfUsers());
        if (auctionRedis.getBidders().size() != 0) {
            saveBidders(keys.get(AuctionRedisKey.BIDDERS), auctionRedis.getBidders());
        }
    }

    private void saveBidders(String key, Set<Bidder> bidders) {
        zSetOps.add(
                key,
                bidders.stream()
                        .map(bidder -> ZSetOperations.TypedTuple.of(bidder.getUserId(), (double) -1 * bidder.getPrice()))
                        .collect(Collectors.toSet()));
    }

    public void delete(Auction auction) {
        Map<AuctionRedisKey, String> keys = AuctionRedisUtil.generateKeys(auction.getId());
        for (String key : keys.values()) {
            redisTemplate.delete(key);
        }
    }

    public AuctionRedis findById(Long auctionId) {
        Map<AuctionRedisKey, String> keys = AuctionRedisUtil.generateKeys(auctionId);
        try {
            Long price = Integer.toUnsignedLong((int) stringOps.get(keys.get(AuctionRedisKey.PRICE)));
            int numberOfUsers = (int) stringOps.get(keys.get(AuctionRedisKey.NUMBER_OF_USERS));
            Set<Bidder> bidders = parseToBidderSet(keys.get(AuctionRedisKey.BIDDERS), 0, -1);
            return AuctionRedis.of(auctionId, price, numberOfUsers, bidders);
        } catch (NullPointerException e) {
            return null;
        }
    }

    private Set<Bidder> parseToBidderSet(String key, int from, int to) {
        Set<DefaultTypedTuple> set = zSetOps.rangeWithScores(key, from, to);
        return set.stream().map((dtt) -> new Bidder(Integer.toUnsignedLong((int) dtt.getValue()), -1 * dtt.getScore().longValue())).collect(Collectors.toSet());
    }

    public Long getPrice(Long auctionId) {
        try {
            Map<AuctionRedisKey, String> keys = AuctionRedisUtil.generateKeys(auctionId);
            return (Long) stringOps.get(keys.get(AuctionRedisKey.PRICE));
        } catch (NullPointerException e) {
            throw null;
        }
    }
}

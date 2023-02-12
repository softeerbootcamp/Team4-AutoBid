package com.codesquad.autobid.auction.repository;

import com.codesquad.autobid.auction.domain.Auction;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class AuctionRedisRepository {

    private final RedisTemplate redisTemplate;

    public AuctionRedisRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(AuctionRedis auctionRedis) {
        Map<AuctionRedisKey, String> keys = AuctionRedisUtil.getRedisKeys(auctionRedis.getId());

        ValueOperations stringOps = redisTemplate.opsForValue();
        stringOps.set(keys.get(AuctionRedisKey.PRICE), auctionRedis.getPrice());
        stringOps.set(keys.get(AuctionRedisKey.NUMBER_OF_USERS), auctionRedis.getNumberOfUsers());

        ZSetOperations zSetOps = redisTemplate.opsForZSet();
        zSetOps.add(keys.get(AuctionRedisKey.BIDDERS), auctionRedis.getBidders());
    }

    public void delete(Auction auction) {
        Map<AuctionRedisKey, String> keys = AuctionRedisUtil.getRedisKeys(auction.getId());
        for (String key : keys.values()) {
            redisTemplate.delete(key);
        }
    }
}

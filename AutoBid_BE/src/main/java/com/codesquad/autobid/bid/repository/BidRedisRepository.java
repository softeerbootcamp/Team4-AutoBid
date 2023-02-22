package com.codesquad.autobid.bid.repository;

import com.codesquad.autobid.auction.repository.AuctionRedisKey;
import com.codesquad.autobid.bid.domain.Bid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class BidRedisRepository {

    private final RedisTemplate redisTemplate;
    private static ZSetOperations zSetOps;
    private static ValueOperations stringOps;

    @Autowired
    public BidRedisRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        zSetOps = redisTemplate.opsForZSet();
        stringOps = redisTemplate.opsForValue();
    }

    public void save(Bid bid) {
        Long auctionId = bid.getAuctionId().getId();
        Map<AuctionRedisKey, String> keys = AuctionRedisKey.generate(auctionId);
        zSetOps.add(keys.get(AuctionRedisKey.BIDDERS), bid.getUserId().getId(), -bid.getPrice());
        stringOps.set(keys.get(AuctionRedisKey.PRICE), bid.getPrice());
    }
}

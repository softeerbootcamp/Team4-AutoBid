package com.codesquad.autobid.bid.repository;

import com.codesquad.autobid.auction.repository.AuctionRedisKey;
import com.codesquad.autobid.auction.repository.AuctionRedisUtil;
import com.codesquad.autobid.bid.domain.Bid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class BidRedisRepository {

    private final RedisTemplate redisTemplate;
    private static ZSetOperations zSetOps;

    @Autowired
    public BidRedisRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        zSetOps = redisTemplate.opsForZSet();
    }

    public void save(Bid bid) {
        Long auctionId = bid.getAuctionId().getId();
        Map<AuctionRedisKey, String> keys = AuctionRedisUtil.generateKeys(auctionId);
        zSetOps.add(keys.get(AuctionRedisKey.BIDDERS), bid.getUserId().getId(), bid.getPrice());
    }
}

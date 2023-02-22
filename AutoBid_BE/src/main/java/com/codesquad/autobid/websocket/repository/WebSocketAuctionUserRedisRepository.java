package com.codesquad.autobid.websocket.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class WebSocketAuctionUserRedisRepository {
    private static String AUCTIONKEY = "auction#";
    private final RedisTemplate redisTemplate;
    private static SetOperations<String, String> setOpsSession;
    private static ValueOperations valueOps;

    @Autowired
    public WebSocketAuctionUserRedisRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        setOpsSession = redisTemplate.opsForSet();
        valueOps = redisTemplate.opsForValue();
    }

    public void saveAuctionUser(Long auctionId, String session) {
        setOpsSession.add(AUCTIONKEY+auctionId, session);
        valueOps.set(session, auctionId);
    }

    public Long countAuctionUsers(Long auctionId) {
        return setOpsSession.size(AUCTIONKEY+auctionId);
    }

    public void deleteAuctionUser(Long auctionId, String session) {
        setOpsSession.remove(AUCTIONKEY + auctionId, session);
    }

    public void deleteAuctionUserAll(Long auctionId) {
        redisTemplate.delete(AUCTIONKEY + auctionId);
    }
}

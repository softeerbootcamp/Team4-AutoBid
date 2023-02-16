package com.codesquad.autobid.websocket.service;

import com.codesquad.autobid.websocket.domain.AuctionWebSocket;
import com.codesquad.autobid.websocket.repository.WebSocketRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebSocketService {

    private final WebSocketRedisRepository webSocketRedisRepository;

    @Autowired
    public WebSocketService(WebSocketRedisRepository webSocketRedisRepository) {
        this.webSocketRedisRepository = webSocketRedisRepository;
    }

    public AuctionWebSocket auctionUserSave(Long auctionId, String session) {
        AuctionWebSocket auctionWebSocket = AuctionWebSocket.of(auctionId, session);
        webSocketRedisRepository.saveAuctionUser(auctionId, session);
        return auctionWebSocket;
    }

    public Long sizeAuctionUsers(Long auctionId) {
        return webSocketRedisRepository.countAuctionUsers(auctionId);
    }

    public AuctionWebSocket deleteAuctionInSession(Long auctionId, String session) {
        AuctionWebSocket auctionWebSocket = AuctionWebSocket.of(auctionId, session);
        webSocketRedisRepository.deleteAuctionUser(auctionId, session);
        return auctionWebSocket;
    }

}

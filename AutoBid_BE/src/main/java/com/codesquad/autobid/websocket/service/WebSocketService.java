package com.codesquad.autobid.websocket.service;

import com.codesquad.autobid.websocket.domain.AuctionUserWebSocket;
import com.codesquad.autobid.websocket.repository.WebSocketAuctionUserRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    private final WebSocketAuctionUserRedisRepository webSocketAuctionUserRedisRepository;

    @Autowired
    public WebSocketService(WebSocketAuctionUserRedisRepository webSocketAuctionUserRedisRepository) {
        this.webSocketAuctionUserRedisRepository = webSocketAuctionUserRedisRepository;
    }

    public AuctionUserWebSocket auctionUserSave(Long auctionId, String session) {
        AuctionUserWebSocket auctionUserWebSocket = AuctionUserWebSocket.of(auctionId, session);
        webSocketAuctionUserRedisRepository.saveAuctionUser(auctionId, session);
        return auctionUserWebSocket;
    }

    public Long sizeAuctionUsers(Long auctionId) {
        return webSocketAuctionUserRedisRepository.countAuctionUsers(auctionId);
    }

    public AuctionUserWebSocket deleteAuctionInSession(Long auctionId, String session) {
        AuctionUserWebSocket auctionUserWebSocket = AuctionUserWebSocket.of(auctionId, session);
        webSocketAuctionUserRedisRepository.deleteAuctionUser(auctionId, session);
        return auctionUserWebSocket;
    }

    public void deleteAuctionAll(Long auctionId) {
        webSocketAuctionUserRedisRepository.deleteAuctionUserAll(auctionId);
    }

}

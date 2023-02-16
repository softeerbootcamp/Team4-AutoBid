package com.codesquad.autobid.websocket.controller;

import com.codesquad.autobid.auction.repository.AuctionRedis;
import com.codesquad.autobid.auction.repository.Bidder;
import com.codesquad.autobid.auction.service.AuctionService;
import com.codesquad.autobid.websocket.domain.AuctionDtoWebSocket;
import com.codesquad.autobid.websocket.domain.AuctionUserWebSocket;
import com.codesquad.autobid.websocket.domain.BidderDto;
import com.codesquad.autobid.websocket.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
public class WebSocketController {
    private final SimpMessageSendingOperations messagingTemplate;
    private final WebSocketService webSocketService;
    private final AuctionService auctionService;


    @Autowired
    public WebSocketController(SimpMessageSendingOperations messagingTemplate, WebSocketService webSocketService, AuctionService auctionService) {
        this.messagingTemplate = messagingTemplate;
        this.webSocketService = webSocketService;
        this.auctionService = auctionService;
    }

    @MessageMapping("/websocket/{auctionId}")
    public void onClientEntered(
            @DestinationVariable(value = "auctionId") Long auctionId,
            SimpMessageHeaderAccessor headerAccessor
    ) { // 사용자 입장 했을 때, 현재 진행중인 경매의 정보를 보여줌
        final String sessionId = headerAccessor.getSessionId();
        log.info("entered auctionId: {}, sessionID: {} ", auctionId, sessionId);
        AuctionRedis auction = auctionService.getAuction(auctionId);
        List<BidderDto> bidderDtoList = webSocketService.bidderToBidderDto(auction.getBidders()); // bidder -> bidderDto
        AuctionDtoWebSocket auctionDtoWebSocket = AuctionDtoWebSocket.of(auction.getPrice(), bidderDtoList); // 현재 입찰가, 입찰자들, 참여자 수
        messagingTemplate.convertAndSend("/subscribe/websocket/" + auctionId, auctionDtoWebSocket);
    }

    @MessageMapping("/websocket/exit/{auctionId}")
    public void finishAuction(@DestinationVariable(value = "auctionId") Long auctionId) {
        webSocketService.deleteAuctionAll(auctionId);
    }

}

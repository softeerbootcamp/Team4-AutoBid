package com.codesquad.autobid.websocket.controller;

import com.codesquad.autobid.auction.repository.AuctionRedis;
import com.codesquad.autobid.auction.service.AuctionService;
import com.codesquad.autobid.websocket.domain.AuctionDtoWebSocket;
import com.codesquad.autobid.websocket.domain.BidderDto;
import com.codesquad.autobid.websocket.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.support.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

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

    @MessageMapping("/enter/all/{auctionId}")
    public void onAllEntered(
            @DestinationVariable(value = "auctionId") Long auctionId
    ) { // convertAndSend
        AuctionRedis auction = auctionService.getAuction(auctionId);
        log.info("auction data : {}",auction);

        messagingTemplate.convertAndSend("/ws/start/" + auctionId, auctionId);
    }

    @MessageMapping("/enter/solo/{auctionId}")
    public void onUserEntered(
            @DestinationVariable(value = "auctionId") Long auctionId,
            Principal principal
    ) { // convertAndSendToUser
        String name = principal.getName();
        log.info("name: {} ##",name);
        AuctionRedis auction = auctionService.getAuction(auctionId);
        List<BidderDto> bidderDtoList = webSocketService.bidderToBidderDto(auction.getBidders()); // bidder -> bidderDto
        AuctionDtoWebSocket auctionDtoWebSocket = AuctionDtoWebSocket.of(auction.getPrice(), bidderDtoList); // 현재 입찰가, 입찰자들, 참여자 수
        messagingTemplate.convertAndSendToUser(principal.getName(),"/ws/start/" + auctionId, createHeaders(name));
    }
    private MessageHeaders createHeaders(@Nullable String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        if (sessionId != null) headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }

//    @MessageMapping("/enter/{auctionId}")
//    public void onClientEntered(
//            @DestinationVariable(value = "auctionId") Long auctionId,
////            Principal principal,
//            SimpMessageHeaderAccessor headerAccessor
//    ) { // 사용자 입장 했을 때, 현재 진행중인 경매의 정보를 보여줌
//        final String sessionId = headerAccessor.getSessionId();
//        log.info("entered auctionId: {}, sessionID: {} ", auctionId, sessionId);
//        AuctionRedis auction = auctionService.getAuction(auctionId);
//        List<BidderDto> bidderDtoList = webSocketService.bidderToBidderDto(auction.getBidders()); // bidder -> bidderDto
//        AuctionDtoWebSocket auctionDtoWebSocket = AuctionDtoWebSocket.of(auction.getPrice(), bidderDtoList); // 현재 입찰가, 입찰자들, 참여자 수
//        messagingTemplate.convertAndSend("/subscribe/enter/" + auctionId, sessionId);
////        messagingTemplate.convertAndSendToUser(principal.getName(),"/ws/start/" + auctionId, sessionId);
//    }
}

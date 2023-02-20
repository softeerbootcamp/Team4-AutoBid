package com.codesquad.autobid.websocket.controller;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.auction.domain.AuctionStatus;
import com.codesquad.autobid.auction.repository.AuctionRedisDTO;
import com.codesquad.autobid.auction.service.AuctionService;
import com.codesquad.autobid.websocket.domain.AuctionDtoWebSocket;
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

    @MessageMapping("/enter/{auctionId}") // 사용자 입장 <경매 시작 후>
    public void onUserEntered(
            @DestinationVariable(value = "auctionId") Long auctionId,
            Principal principal
    ) { // convertAndSendToUser
        String name = principal.getName();
        log.info("name: {} ",name);

        AuctionRedisDTO auctionRedis = auctionService.getAuction(auctionId);
        if (auctionRedis != null) { // 시작된 경우
            AuctionDtoWebSocket auctionDtoWebSocket = webSocketService.parsingDto(auctionRedis);
            messagingTemplate.convertAndSendToUser(principal.getName(),"/ws/start/" + auctionId, auctionDtoWebSocket);
            return;
        }
        Auction auctionDb = auctionService.getDBAuction(auctionId);
        AuctionStatus auctionStatus = auctionDb.getAuctionStatus();
        if (auctionStatus == AuctionStatus.COMPLETED){ // auction 이 종료된 경우
            AuctionDtoWebSocket auctionDtoWebSocket = webSocketService.parsingDto(auctionDb);
            messagingTemplate.convertAndSendToUser(principal.getName(),"/ws/end/" + auctionId, auctionDtoWebSocket);
        }
    }

    @MessageMapping("/end/{auctionId}")
    public void exitAll(
            @DestinationVariable(value = "auctionId") Long auctionId
    ) {
        messagingTemplate.convertAndSend("/ws/end/" + auctionId, "exit");
    }

    private MessageHeaders createHeaders(@Nullable String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        if (sessionId != null) headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }
}

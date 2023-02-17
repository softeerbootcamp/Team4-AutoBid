package com.codesquad.autobid.websocket.controller;

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

@Slf4j
@RestController
public class WebSocketController {
    private final SimpMessageSendingOperations messagingTemplate;
    private final WebSocketService webSocketService;

    @Autowired
    public WebSocketController(SimpMessageSendingOperations messagingTemplate, WebSocketService webSocketService) {
        this.messagingTemplate = messagingTemplate;
        this.webSocketService = webSocketService;
    }

    @MessageMapping("/enter/{auctionId}")
    public void onAllEntered(
            @DestinationVariable(value = "auctionId") Long auctionId
    ) { // convertAndSend
        AuctionDtoWebSocket auctionDtoWebSocket = webSocketService.parsingDto(auctionId);
        messagingTemplate.convertAndSend("/ws/start/" + auctionId, auctionDtoWebSocket);
    }

    @MessageMapping("/enter/solo/{auctionId}")
    public void onUserEntered(
            @DestinationVariable(value = "auctionId") Long auctionId,
            Principal principal
    ) { // convertAndSendToUser
        String name = principal.getName();
        log.info("name: {} ",name);
        AuctionDtoWebSocket auctionDtoWebSocket = webSocketService.parsingDto(auctionId);
        messagingTemplate.convertAndSendToUser(principal.getName(),"/ws/start/" + auctionId, auctionDtoWebSocket);
    }

    private MessageHeaders createHeaders(@Nullable String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        if (sessionId != null) headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }
}

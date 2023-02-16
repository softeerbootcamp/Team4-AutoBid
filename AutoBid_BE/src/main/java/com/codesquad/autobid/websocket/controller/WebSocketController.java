package com.codesquad.autobid.websocket.controller;

import com.codesquad.autobid.websocket.domain.AuctionUserWebSocket;
import com.codesquad.autobid.websocket.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

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

    @MessageMapping("/websocket/{auctionId}")
    public void onClientEntered(
            @DestinationVariable(value = "auctionId") Long auctionId,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        final String sessionId = headerAccessor.getSessionId();
        log.info("entered auctionId: {}, sessionID: {} ", auctionId, sessionId);
        AuctionUserWebSocket auctionUserWebSocket = webSocketService.auctionUserSave(auctionId, sessionId);
        Long auctionSize = webSocketService.sizeAuctionUsers(auctionId);
        log.info("auctinoSize : {}", auctionSize);
        messagingTemplate.convertAndSend("/subscribe/websocket/" + auctionId, auctionUserWebSocket);
    }

    @MessageMapping("/websocket/exit/{auctionId}")
    public void exitRoom() {

    }
}

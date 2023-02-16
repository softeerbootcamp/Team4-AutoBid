package com.codesquad.autobid.websocket.listener;

import com.codesquad.autobid.websocket.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@Component
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;
    private final WebSocketService webSocketService;

    @Autowired
    public WebSocketEventListener(SimpMessageSendingOperations messagingTemplate, WebSocketService webSocketService) {
        this.messagingTemplate = messagingTemplate;
        this.webSocketService = webSocketService;
    }


    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//        String sessionId = headerAccessor.getSessionId();
        log.info("Close sessionID: {}",event.getMessage());
    }
}


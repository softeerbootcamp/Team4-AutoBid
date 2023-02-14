package com.codesquad.autobid.websocket.controller;

import com.codesquad.autobid.auction.request.AuctionRegisterRequest;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.websocket.service.WebSocketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@RestController
public class WebSocketController {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public WebSocketService webSocketService;

    /**
     * TODO
     *  - webSocket#1, sessionID 의 형태로 레디스에 저장
     *  - subscribe로 현재 방 인원, BidList를 담아서줌
     * **/

    @MessageMapping("/websocket/{auctionId}")
    public void onClientEntered(
            @DestinationVariable(value = "auctionId") Long auctionId,
            @Payload String body,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        final String sessionId = headerAccessor.getSessionId();
        log.info("entered auctionId: {}, " + sessionId, auctionId);
        log.info(body);
        headerAccessor.getSessionAttributes().put("session", sessionId); // 웹 소켓 연결 종료에 대한 이벤트를 만들기 위한 메서드
        messagingTemplate.convertAndSend("/subscribe/websocket/" + auctionId, body);
    }
}

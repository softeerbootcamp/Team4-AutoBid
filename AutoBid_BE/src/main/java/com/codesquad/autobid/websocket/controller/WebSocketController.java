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

    @GetMapping("/enter/{auctionNum}")
    public HttpEntity<HttpStatus> enterRoom(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        Optional<User> user = (Optional<User>) session.getAttribute("user");
        if (user.isPresent()) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/enter/{roomId}")
    public void post(@PathVariable(value = "roomId") Long roomId) {
        messagingTemplate.convertAndSend("/subscribe/new/" + roomId, "asss");
    }

    @MessageMapping("/enter/{roomId}")
    public void onClientEntered(
            @DestinationVariable(value = "roomId") Long roomId,
            @Payload String body,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        final String sessionId = headerAccessor.getSessionId();
        log.info("entered roomId: {}, " + sessionId, roomId);
        log.info(body);
        headerAccessor.getSessionAttributes().put("username", body); // 웹 소켓 연결 종료에 대한 이벤트를 만들기 위한 메서드
        messagingTemplate.convertAndSend("/subscribe/enter/" + roomId, body);
    }
}

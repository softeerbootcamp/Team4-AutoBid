package com.codesquad.autobid.auction.controller;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.user.domain.AuctionRoomDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

@Slf4j
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuctionControllerTest {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/createRoom") // 클라이언트에서 보내는 메세지 매핑
    public void createRoom(@Payload Auction auction) {
        log.info("auction data : {} 생성", auction.getId());
        messagingTemplate.convertAndSend("/subscribe/auction/" + auction.getId(), auction); // 방을 만들어달라는 메세지
    }

    @MessageMapping("/enterRoom") // 클라이언트에서 보내는 메세지 매핑
    public void enterRoom(@Payload AuctionRoomDto auctionRoomDto) {
        log.info("{} 님이 {} 방에 입장", auctionRoomDto.getUserName(), auctionRoomDto.getAuctionId());
        messagingTemplate.convertAndSend("/subscribe/auction/" + auctionRoomDto.getAuctionId(), auctionRoomDto);
        // 해당 방으로 입장한다는 메세지
    }
}

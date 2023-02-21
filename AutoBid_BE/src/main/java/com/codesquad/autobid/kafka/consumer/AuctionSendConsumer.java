package com.codesquad.autobid.kafka.consumer;

import com.codesquad.autobid.auction.domain.AuctionStatus;
import com.codesquad.autobid.kafka.producer.dto.AuctionKafkaDTO;
import com.codesquad.autobid.websocket.domain.AuctionDtoWebSocket;
import com.codesquad.autobid.websocket.domain.BidderDto;
import com.codesquad.autobid.websocket.service.WebSocketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuctionSendConsumer {

    private final WebSocketService webSocketService;
    private final ObjectMapper om;

    @KafkaListener(topics = "auction-send", groupId = "auction-send-consumer")
    public void consume(String json) throws JsonProcessingException {
        log.debug("AuctionSendConsumer: {}", json);
        AuctionKafkaDTO auctionKafkaDTO = om.readValue(json, AuctionKafkaDTO.class);
        String url = getUrl(auctionKafkaDTO);
        List<BidderDto> bidderUser = auctionKafkaDTO.getUsers().stream().map(BidderDto::from).collect(Collectors.toList());
        AuctionDtoWebSocket auctionDtoWebSocket = AuctionDtoWebSocket.of(auctionKafkaDTO.getPrice(), bidderUser);

        log.info("json-string:{}", json);
        log.info("get-price:{}",auctionKafkaDTO.getPrice());
        log.info("user-count:{}",auctionDtoWebSocket.getNumberOfUsers());
        log.info("user-price:{}",auctionDtoWebSocket.getPrice());
        log.info("broadcast:{}", url);
        webSocketService.broadCast(auctionDtoWebSocket,url);
    }

    private String getUrl(AuctionKafkaDTO auctionKafkaDTO) {
        // 오츤 프로듀서 -> 오픈 어댑터 -> 오픈 프로듀서
        if (auctionKafkaDTO.getAuctionStatus() == AuctionStatus.PROGRESS) {
            return "/end/" + auctionKafkaDTO.getAuctionId(); // 경매 진행 중 -> 종료
        }
        return "/start/" + auctionKafkaDTO.getAuctionId(); // 경매 시작 전 -> 진행
    }
}

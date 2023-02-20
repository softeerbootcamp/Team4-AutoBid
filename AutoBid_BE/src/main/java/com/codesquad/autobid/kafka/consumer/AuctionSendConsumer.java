package com.codesquad.autobid.kafka.consumer;

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
        String url = "/end" + auctionKafkaDTO.getAuctionId();
        List<BidderDto> bidderUser = auctionKafkaDTO.getUsers().stream().map(BidderDto::from).collect(Collectors.toList());
        AuctionDtoWebSocket auctionDtoWebSocket = AuctionDtoWebSocket.of(auctionKafkaDTO.getPrice(), bidderUser);

        webSocketService.broadCast(auctionDtoWebSocket,url);
    }
}

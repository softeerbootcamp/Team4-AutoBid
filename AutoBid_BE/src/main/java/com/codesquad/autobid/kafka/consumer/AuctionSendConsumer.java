package com.codesquad.autobid.kafka.consumer;

import com.codesquad.autobid.kafka.producer.dto.AuctionKafkaDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuctionSendConsumer {

    // private final SocketController socketController;

    @KafkaListener(topics = "auction-send", groupId = "auction-send-consumer")
    public void consume(@Payload AuctionKafkaDTO auctionKafkaDTO) {
        log.debug("AuctionSendConsumer: {}", auctionKafkaDTO);
        // todo: socketController 연결
        // socketController.send(auction);
    }
}

package com.codesquad.autobid.kafka.consumer;

import com.codesquad.autobid.auction.domain.Auction;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuctionSendConsumer {

    // private final SocketController socketController;

    @KafkaListener(topics = "send-auction-socket")
    public void consume(Auction auction) {
        // todo: socketController 연결
        // socketController.send(auction);
    }
}

package com.codesquad.autobid.kafka.producer;

import com.codesquad.autobid.auction.domain.Auction;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuctionOpenProducer {
    @Value("${spring.kafka.topic.auction-open}")
    private String AUCTION_OPEN_TOPIC_NAME;

    private final KafkaTemplate kafkaTemplate;

    public void produce(List<Auction> auctions) {
        // todo: 예외 처리
        for (Auction auction : auctions) {
            kafkaTemplate.send(AUCTION_OPEN_TOPIC_NAME, auction);
        }
    }
}

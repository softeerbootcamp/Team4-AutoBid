package com.codesquad.autobid.kafka.adapter;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.auction.repository.AuctionRedis;
import com.codesquad.autobid.auction.repository.AuctionRedisRepository;
import com.codesquad.autobid.auction.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuctionOpenAdapter {

    @Value("${spring.kafka.topic.auction-send}")
    private String AUCTION_SEND_TOPIC_NAME;

    private final KafkaTemplate kafkaTemplate;
    private final AuctionRedisRepository auctionRedisRepository;
    private final AuctionRepository auctionRepository;


    @KafkaListener(topics = "auction-open")
    public void consume(Auction auction) {
        // todo: check deserialize
        auction.open();
        auctionRepository.save(auction);
        auctionRedisRepository.save(AuctionRedis.from(auction));
        // call produce
        produce(auction);
    }

    private void produce(Auction auction) {
        // todo: check serialize
        kafkaTemplate.send(AUCTION_SEND_TOPIC_NAME, auction);
    }
}

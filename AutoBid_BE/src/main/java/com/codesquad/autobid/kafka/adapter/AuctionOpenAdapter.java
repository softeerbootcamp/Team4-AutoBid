package com.codesquad.autobid.kafka.adapter;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.auction.repository.AuctionRedis;
import com.codesquad.autobid.auction.repository.AuctionRedisRepository;
import com.codesquad.autobid.auction.repository.AuctionRepository;
import com.codesquad.autobid.kafka.producer.dto.AuctionKafkaDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuctionOpenAdapter {

    @Value("${spring.kafka.topic.auction-send}")
    private String AUCTION_SEND_TOPIC_NAME;

    private final KafkaTemplate kafkaTemplate;
    private final AuctionRedisRepository auctionRedisRepository;
    private final AuctionRepository auctionRepository;


    @KafkaListener(topics = "auction-open", groupId = "auction-open-consumer")
    public void consume(@Payload AuctionKafkaDTO auctionKafkaDTO) {
        log.debug("AuctionOpenAdapter: {}", auctionKafkaDTO.getAuction());
        Auction auction = auctionKafkaDTO.getAuction();
        // todo: check deserialize
        auction.open();
        auctionRepository.save(auction);
        auctionRedisRepository.save(AuctionRedis.from(auction));
        produce(auctionKafkaDTO);
    }

    private void produce(AuctionKafkaDTO auctionKafkaDTO) {
        // todo: check serialize
        kafkaTemplate.send(AUCTION_SEND_TOPIC_NAME, auctionKafkaDTO);
    }
}

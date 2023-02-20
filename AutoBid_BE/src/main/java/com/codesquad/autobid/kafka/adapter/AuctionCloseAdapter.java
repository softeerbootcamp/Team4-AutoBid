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
public class AuctionDataCloseAdapter {

    @Value("${spring.kafka.topic.auction-email}")
    private String AUCTION_EMAIL_TOPIC_NAME;
    @Value("${spring.kafka.topic.auction-send}")
    private String AUCTION_SEND_TOPIC_NAME;

    private final KafkaTemplate kafkaTemplate;
    private final AuctionRedisRepository auctionRedisRepository;
    private final AuctionRepository auctionRepository;

    @KafkaListener(topics = "auction-close", groupId = "auction-close-consumer")
    public void consume(Auction auction) {
        // todo : auctionRedis 정리가 구체적으로 어떤 작업인가?
        AuctionRedis auctionRedis = auctionRedisRepository.findById(auction.getId());
        /*
            {
            	price: long,
            	users: [ // 5명
            		{
            			userId: long,
            			username: string,
            			phoneNumber: string,
            			price: long
            		},
            	],
            	numberOfUsers: long
            }
         */
        // mysql
        auction.close();
        auctionRepository.save(auction);
        produce(auction);
    }

    public void produce(Auction auction) {
        kafkaTemplate.send(AUCTION_EMAIL_TOPIC_NAME, auction);
        kafkaTemplate.send(AUCTION_SEND_TOPIC_NAME, auction);
    }
}

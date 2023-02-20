package com.codesquad.autobid.kafka.adapter;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.auction.repository.AuctionRedisBidderDTO;
import com.codesquad.autobid.auction.repository.AuctionRedisDTO;
import com.codesquad.autobid.auction.repository.AuctionRedisRepository;
import com.codesquad.autobid.auction.repository.AuctionRepository;
import com.codesquad.autobid.kafka.producer.dto.AuctionKafkaDTO;
import com.codesquad.autobid.kafka.producer.dto.AuctionKafkaUserDTO;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuctionCloseAdapter {

    @Value("${spring.kafka.topic.auction-email}")
    private String AUCTION_EMAIL_TOPIC_NAME;
    @Value("${spring.kafka.topic.auction-send}")
    private String AUCTION_SEND_TOPIC_NAME;

    private final KafkaTemplate kafkaTemplate;
    private final AuctionRedisRepository auctionRedisRepository;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;

    @KafkaListener(topics = "auction-close", groupId = "auction-close-consumer")
    public void consume(@Payload AuctionKafkaDTO auctionKafkaDTO) {
        // todo : auctionRedis 정리가 구체적으로 어떤 작업인가?
        // redis
        Auction auction = auctionKafkaDTO.getAuction();
        AuctionRedisDTO auctionRedisDto = auctionRedisRepository.findById(auction.getId());
        List<AuctionKafkaUserDTO> auctionKafkaUserDTOs = findBidders(auctionRedisDto.getAuctionRedisBidderDto());
        auctionRedisRepository.delete(auction);

        // mysql
        auction.markToFinish(auctionRedisDto.getPrice());
        auctionRepository.save(auction);

        auctionKafkaDTO.update(auctionKafkaUserDTOs);
        produce(auctionKafkaDTO);
    }

    private List<AuctionKafkaUserDTO> findBidders(Set<AuctionRedisBidderDTO> auctionRedisBidderDtos) {
        return auctionRedisBidderDtos.stream()
            .map(auctionBidderDTO -> {
                User user = userRepository.findById(auctionBidderDTO.getUserId()).get();
                return AuctionKafkaUserDTO.from(auctionBidderDTO, user);
            })
            .collect(Collectors.toList());
    }

    public void produce(AuctionKafkaDTO auctionKafkaDto) {
        // todo: check serializer
        kafkaTemplate.send(AUCTION_EMAIL_TOPIC_NAME, auctionKafkaDto);
        kafkaTemplate.send(AUCTION_SEND_TOPIC_NAME, auctionKafkaDto);
    }
}

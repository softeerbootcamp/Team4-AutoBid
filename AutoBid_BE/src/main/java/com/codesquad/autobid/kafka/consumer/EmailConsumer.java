package com.codesquad.autobid.kafka.consumer;

import com.codesquad.autobid.email.EmailService;
import com.codesquad.autobid.kafka.producer.dto.AuctionKafkaDTO;
import com.codesquad.autobid.kafka.producer.dto.AuctionKafkaUserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailConsumer {

    private final EmailService emailService;
    private final ObjectMapper om;

    @KafkaListener(topics = "auction-email", groupId = "auction-email-consumer")
    public void consume(String json) throws JsonProcessingException {
        log.error("EmailConsumer: {}", json);
        AuctionKafkaDTO auctionKafkaDTO = om.readValue(json, AuctionKafkaDTO.class);
        List<AuctionKafkaUserDTO> users = auctionKafkaDTO.getUsers();

        for (AuctionKafkaUserDTO user : users) {
            String auctionTitle = auctionKafkaDTO.getAuctionTitle();
            Long endPrice = auctionKafkaDTO.getPrice();
            emailService.send(auctionTitle, endPrice, user);
        }
    }
}

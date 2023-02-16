package com.codesquad.autobid.kafka.consumer;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.email.EmailService;
import com.codesquad.autobid.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailConsumer {

    private final EmailService emailService;

    @KafkaListener(topics = "auction-email")
    public void consumeMessage(Auction auction) {
        emailService.send(auction, new User(), 1000l);
    }
}

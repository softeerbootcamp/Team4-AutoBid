package com.codesquad.autobid.email;

import com.codesquad.autobid.kafka.producer.dto.AuctionKafkaUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String USERNAME;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void send(String auctionTitle, Long endPrice, AuctionKafkaUserDTO user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(USERNAME);
        message.setTo(user.getEmail());
        message.setSubject(buildTitle(auctionTitle));
        message.setText(buildContent(auctionTitle, endPrice, user.getName(), user.getPrice()));
        javaMailSender.send(message);
    }

    private String buildContent(String auctionTitle, Long endPrice, String username, Long userPrice) {
        StringBuilder sb = new StringBuilder();
        sb.append(auctionTitle).append("의 최종 가격은").append(endPrice).append("만원 입니다. \n");
        sb.append(username).append("님의 최종 입찰 가격은").append(userPrice).append("만원 입니다.");
        return sb.toString();
    }

    private String buildTitle(String auctionTitle) {
        StringBuilder sb = new StringBuilder();
        sb.append(auctionTitle).append(" 경매 결과를 알려드립니다.");
        return sb.toString();
    }
}

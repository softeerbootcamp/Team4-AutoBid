package com.codesquad.autobid.email;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.user.domain.User;
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

    public boolean send(Auction auction, User bidOwner, Long price) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(USERNAME);
            message.setTo(bidOwner.getEmail());
            message.setSubject(buildTitle(auction));
            message.setText(buildContent(auction, bidOwner, price));
            javaMailSender.send(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String buildContent(Auction auction, User bidOwner, Long price) {
        StringBuilder sb = new StringBuilder();
        sb.append(auction.getId()).append("의 최종 가격은").append(auction.getAuctionEndPrice()).append("원 입니다. \n");
        sb.append(bidOwner.getName()).append("님의 최종 입찰 가격은").append(price).append("원 입니다.");
        return sb.toString();
    }

    private String buildTitle(Auction auction) {
        return "경매 결과를 알려드립니다.";
    }
}

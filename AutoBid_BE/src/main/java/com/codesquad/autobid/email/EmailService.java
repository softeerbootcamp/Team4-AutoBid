package com.codesquad.autobid.email;

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
    
    public void send(String address) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(USERNAME);
        message.setTo(address);
        message.setSubject("title");
        message.setText("content");
        javaMailSender.send(message);
    }
}

package com.codesquad.autobid.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    List<Integer> testaAuctionRoomId = new ArrayList<>();

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        testaAuctionRoomId.add(1);
        testaAuctionRoomId.add(2);
        testaAuctionRoomId.add(3);
        testaAuctionRoomId.add(4);
        registry.addEndpoint("/auction-room").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/publish");
        registry.enableSimpleBroker("/subscribe");
    }
}

package com.codesquad.autobid.config;

import com.codesquad.autobid.auction.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * TODO
     *   1. 방이 만들어질 리스트를 가져온다.
     *   2. 해당 방들에 대해 웹 소켓을 연결해준다.
     **/

    List<Integer> testaAuctionRoomId = new ArrayList<>();

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        testaAuctionRoomId.add(1);
        testaAuctionRoomId.add(2);
        testaAuctionRoomId.add(3);
        testaAuctionRoomId.add(4);
        for (Integer i : testaAuctionRoomId) {
            registry.addEndpoint("/auction-room/"+i+"/").setAllowedOriginPatterns("http://localhost:8080","http://localhost:5500","http://localhost:3000")
                    .withSockJS();
        }
//        registry.addEndpoint("/auction-room/").setAllowedOriginPatterns("http://localhost:8080","http://localhost:5500","http://localhost:3000")
//                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/publish");
        registry.enableSimpleBroker("/subscribe");
    }
}

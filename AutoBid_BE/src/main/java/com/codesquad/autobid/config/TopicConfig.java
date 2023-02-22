package com.codesquad.autobid.config.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class TopicConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String BOOTSTRAP_SERVERS;
    @Value("${spring.kafka.topic.auction-open}")
    private String AUCTION_OPEN_TOPIC_NAME;
    @Value("${spring.kafka.topic.auction-send}")
    private String AUCTION_SEND_TOPIC_NAME;
    @Value("${spring.kafka.topic.auction-close}")
    private String AUCTION_CLOSE_TOPIC_NAME;
    @Value("${spring.kafka.topic.auction-email}")
    private String AUCTION_EMAIL_TOPIC_NAME;

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        return new KafkaAdmin(configs);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

    @Bean
    public NewTopic auctionOpen() {
        return TopicBuilder.name(AUCTION_OPEN_TOPIC_NAME).build();
    }

    @Bean
    public NewTopic auctionClose() {
        return TopicBuilder.name(AUCTION_CLOSE_TOPIC_NAME).build();
    }

    @Bean
    public NewTopic auctionSend() {
        return TopicBuilder.name(AUCTION_SEND_TOPIC_NAME).build();
    }

    @Bean
    public NewTopic auctionEmail() {
        return TopicBuilder.name(AUCTION_EMAIL_TOPIC_NAME).build();
    }
}

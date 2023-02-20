package com.codesquad.autobid.config.kafka;

import com.codesquad.autobid.config.kafka.serializer.AuctionKafkaDTOSerializer;
import com.codesquad.autobid.kafka.producer.dto.AuctionKafkaDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Configuration
@EnableKafka
@Slf4j
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String BOOTSTRAP_SERVERS;

    // @Bean
    // public KafkaTemplate<String, AuctionKafkaDTO> auctionKafkaTemplate() {
    //     Map<String, Object> props = new HashMap<>();
    //     props.put(BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
    //     props.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    //     props.put(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    //     return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(props));
    // }
}

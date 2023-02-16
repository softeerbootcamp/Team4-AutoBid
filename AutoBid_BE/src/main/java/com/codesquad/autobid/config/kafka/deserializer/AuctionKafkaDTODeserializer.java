package com.codesquad.autobid.config.kafka.deserializer;

import com.codesquad.autobid.kafka.producer.dto.AuctionKafkaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

@Slf4j
public class AuctionKafkaDTODeserializer implements Deserializer<AuctionKafkaDTO> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public AuctionKafkaDTO deserialize(String topic, byte[] data) {
        System.out.println(topic);
        System.out.println(data);
        try {
            return objectMapper.readValue(data, AuctionKafkaDTO.class);
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
        return null;
    }
}

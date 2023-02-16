package com.codesquad.autobid.config.kafka.serializer;

import com.codesquad.autobid.kafka.producer.dto.AuctionKafkaDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;

@Slf4j
public class AuctionKafkaDTOSerializer implements Serializer<AuctionKafkaDTO> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, AuctionKafkaDTO data) {
        // todo: 더 알아보고 예외 상황 잡기
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(data);
            for (byte aByte : bytes) {
                System.out.println((char) aByte);
            }
            return bytes;
        } catch (JsonProcessingException e) {
            log.debug("AuctionSerializer Error");
        }
        return new byte[0];
    }
}

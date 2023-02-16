package com.codesquad.autobid.kafka.producer.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuctionKafkaUserDTO {

    private Long id;
    private String name;
    private String phoneNumber;
    private Long price;

    public static AuctionKafkaUserDTO of(Long id, String name, String phoneNumber, Long price) {
        AuctionKafkaUserDTO auctionUserDTO = new AuctionKafkaUserDTO();
        auctionUserDTO.id = id;
        auctionUserDTO.name = name;
        auctionUserDTO.phoneNumber = phoneNumber;
        auctionUserDTO.price = price;
        return auctionUserDTO;
    }
}

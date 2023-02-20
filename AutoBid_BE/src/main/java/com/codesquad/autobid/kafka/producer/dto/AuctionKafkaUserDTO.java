package com.codesquad.autobid.kafka.producer.dto;

import com.codesquad.autobid.auction.repository.AuctionRedisBidderDTO;
import com.codesquad.autobid.user.domain.User;
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
    private String email;

    public static AuctionKafkaUserDTO of(Long id, String name, String phoneNumber, Long price, String email) {
        AuctionKafkaUserDTO auctionKafkaUserDTO = new AuctionKafkaUserDTO();
        auctionKafkaUserDTO.id = id;
        auctionKafkaUserDTO.name = name;
        auctionKafkaUserDTO.phoneNumber = phoneNumber;
        auctionKafkaUserDTO.price = price;
        auctionKafkaUserDTO.email = email;
        return auctionKafkaUserDTO;
    }

    public static AuctionKafkaUserDTO from(AuctionRedisBidderDTO auctionRedisBidderDTO, User user) {
        AuctionKafkaUserDTO auctionKafkaUserDTO = new AuctionKafkaUserDTO();
        auctionKafkaUserDTO.id = auctionRedisBidderDTO.getUserId();
        auctionKafkaUserDTO.name = user.getName();
        auctionKafkaUserDTO.phoneNumber = user.getMobilenum();
        auctionKafkaUserDTO.price = auctionRedisBidderDTO.getPrice();
        return auctionKafkaUserDTO;
    }
}

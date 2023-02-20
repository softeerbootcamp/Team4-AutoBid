package com.codesquad.autobid.websocket.domain;

import com.codesquad.autobid.kafka.producer.dto.AuctionKafkaDTO;
import com.codesquad.autobid.kafka.producer.dto.AuctionKafkaUserDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BidderDto {
    private Long userId;
    private String username;
    private String phoneNumber;
    private Long price;

    public static BidderDto from(AuctionKafkaUserDTO auctionKafkaUserDTO) {
        return new BidderDto(auctionKafkaUserDTO.getId(), auctionKafkaUserDTO.getName(), auctionKafkaUserDTO.getPhoneNumber(), auctionKafkaUserDTO.getPrice());
    }

    public BidderDto(Long userId, String userName, String phoneNumber, Long price) {
        this.userId = userId;
        this.username = userName;
        this.phoneNumber = phoneNumber;
        this.price = price;
    }

    public BidderDto() {
    }
}

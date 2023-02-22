package com.codesquad.autobid.websocket.domain;

import com.codesquad.autobid.kafka.producer.dto.AuctionKafkaUserDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BidderDto {
    private Long userId;
    private String username;
    private String phoneNumber;
    private Long price;

    public static BidderDto from(AuctionKafkaUserDTO auctionKafkaUserDTO) {
        BidderDto bidderDto = new BidderDto();
        bidderDto.userId = auctionKafkaUserDTO.getId();
        bidderDto.username = auctionKafkaUserDTO.getName();
        bidderDto.phoneNumber = auctionKafkaUserDTO.getPhoneNumber();
        bidderDto.price = auctionKafkaUserDTO.getPrice();
        return bidderDto;
    }

    public static BidderDto of(Long userId, Long price, String name, String mobilenum) {
        BidderDto bidderDto = new BidderDto();
        bidderDto.userId = userId;
        bidderDto.username = name;
        bidderDto.phoneNumber = mobilenum;
        bidderDto.price = price;
        return bidderDto;
    }
}

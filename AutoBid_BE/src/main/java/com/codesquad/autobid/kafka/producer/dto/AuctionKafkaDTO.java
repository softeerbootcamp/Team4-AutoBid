package com.codesquad.autobid.kafka.producer.dto;

import com.codesquad.autobid.auction.domain.Auction;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class AuctionKafkaDTO {
    private Auction auction;
    private Long price;
    private List<AuctionKafkaUserDTO> user;
    private Long numberOfUsers;

    public static AuctionKafkaDTO from(Auction auction) {
        AuctionKafkaDTO auctionKafkaDTO = new AuctionKafkaDTO();
        auctionKafkaDTO.auction = auction;
        auctionKafkaDTO.price = auction.getAuctionStartPrice();
        auctionKafkaDTO.user = new ArrayList<>();
        auctionKafkaDTO.numberOfUsers = 0l;
        return auctionKafkaDTO;
    }
}

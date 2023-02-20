package com.codesquad.autobid.kafka.producer.dto;

import com.codesquad.autobid.auction.domain.Auction;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuctionKafkaDTO {

    private Long auctionId;
    private String auctionTitle;
    private Long price;
    private List<AuctionKafkaUserDTO> users;
    private int numberOfUsers;

    public static AuctionKafkaDTO from(Auction auction) {
        AuctionKafkaDTO auctionKafkaDTO = new AuctionKafkaDTO();
        auctionKafkaDTO.auctionId = auction.getId();
        auctionKafkaDTO.auctionTitle = auction.getAuctionTitle();
        auctionKafkaDTO.price = auction.getAuctionStartPrice();
        auctionKafkaDTO.users = new ArrayList<>();
        auctionKafkaDTO.numberOfUsers = 0;
        return auctionKafkaDTO;
    }

    public void update(List<AuctionKafkaUserDTO> auctionKafkaUserDTOs) {
        this.users = auctionKafkaUserDTOs;
        this.numberOfUsers = auctionKafkaUserDTOs.size();
    }
}

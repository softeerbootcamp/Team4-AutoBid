package com.codesquad.autobid.kafka.producer.dto;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.auction.repository.AuctionRedisDTO;
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
    private String auctionTitle;
    private Long price;
    private List<AuctionKafkaUserDTO> users;
    private int numberOfUsers;

    public static AuctionKafkaDTO from(Auction auction) {
        AuctionKafkaDTO auctionKafkaDTO = new AuctionKafkaDTO();
        auctionKafkaDTO.auction = auction;
        auctionKafkaDTO.auctionTitle = auction.getAuctionTitle();
        auctionKafkaDTO.price = auction.getAuctionStartPrice();
        auctionKafkaDTO.users = new ArrayList<>();
        auctionKafkaDTO.numberOfUsers = 0;
        return auctionKafkaDTO;
    }

    public static AuctionKafkaDTO of(AuctionRedisDTO auctionRedisDTO, String auctionTitle, List<AuctionKafkaUserDTO> bidders) {
        AuctionKafkaDTO auctionKafkaDTO = new AuctionKafkaDTO();
        auctionKafkaDTO.auctionTitle = auctionTitle;
        auctionKafkaDTO.price = auctionRedisDTO.getPrice();
        auctionKafkaDTO.users = bidders;
        auctionKafkaDTO.numberOfUsers = auctionRedisDTO.getAuctionRedisBidderDto().size();
        return auctionKafkaDTO;
    }

    public void update(List<AuctionKafkaUserDTO> auctionKafkaUserDTOs) {
        this.users = auctionKafkaUserDTOs;
        this.numberOfUsers = auctionKafkaUserDTOs.size();
    }
}

package com.codesquad.autobid.auction.repository;

import com.codesquad.autobid.auction.domain.Auction;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AuctionRedisDTO {

    private Long auctionId;

    private Long price;

    private List<AuctionRedisBidderDTO> auctionRedisBidderDto = new ArrayList<>();

    private AuctionRedisDTO(Long auctionId, Long price) {
        this.auctionId = auctionId;
        this.price = price;
    }

    public static AuctionRedisDTO from(Auction auction) {
        return new AuctionRedisDTO(auction.getId(), auction.getAuctionStartPrice());
    }

    public static AuctionRedisDTO of(Long auctionId, Long price, List<AuctionRedisBidderDTO> auctionRedisBidderDTOS) {
        AuctionRedisDTO auctionRedisDto = new AuctionRedisDTO(auctionId, price);
        auctionRedisDto.auctionRedisBidderDto = auctionRedisBidderDTOS;
        return auctionRedisDto;
    }
}

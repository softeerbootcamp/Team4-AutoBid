package com.codesquad.autobid.auction.repository;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuctionBidderDTO {

    private Long userId;
    private Long price;
    public static AuctionBidderDTO of(Long userId, Long price) {
        AuctionBidderDTO auctionBidderDTO = new AuctionBidderDTO();
        auctionBidderDTO.userId = userId;
        auctionBidderDTO.price = price;
        return auctionBidderDTO;
    }
}

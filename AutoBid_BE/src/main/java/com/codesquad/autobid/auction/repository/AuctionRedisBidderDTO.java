package com.codesquad.autobid.auction.repository;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuctionRedisBidderDTO {

    private Long userId;
    private Long price;
    public static AuctionRedisBidderDTO of(Long userId, Long price) {
        AuctionRedisBidderDTO auctionRedisBidderDTO = new AuctionRedisBidderDTO();
        auctionRedisBidderDTO.userId = userId;
        auctionRedisBidderDTO.price = price;
        return auctionRedisBidderDTO;
    }
}

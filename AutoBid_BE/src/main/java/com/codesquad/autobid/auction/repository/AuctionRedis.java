package com.codesquad.autobid.auction.repository;

import com.codesquad.autobid.auction.domain.Auction;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class AuctionRedis {

    private Long id;

    private Long price;

    private Integer numberOfUsers = 0;

    private Set<Bidder> bidders = new HashSet<>();

    private AuctionRedis(Long id, Long price) {
        this.id = id;
        this.price = price;
    }

    public static AuctionRedis from(Auction auction) {
        return new AuctionRedis(auction.getId(), auction.getAuctionStartPrice());
    }
}

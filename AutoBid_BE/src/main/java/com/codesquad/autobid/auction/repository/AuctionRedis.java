package com.codesquad.autobid.auction.repository;

import com.codesquad.autobid.auction.domain.Auction;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class AuctionRedis {

    private Long id;

    private Long price;

    private Set<Bidder> bidders = new HashSet<>();

    private AuctionRedis(Long id, Long price) {
        this.id = id;
        this.price = price;
    }

    public static AuctionRedis from(Auction auction) {
        return new AuctionRedis(auction.getId(), auction.getAuctionStartPrice());
    }

    public static AuctionRedis of(Long auctionId, Long price, Set<Bidder> bidders) {
        AuctionRedis auctionRedis = new AuctionRedis(auctionId, price);
        auctionRedis.bidders = bidders;
        return auctionRedis;
    }

    public void addBidder(Bidder bidder) {
        bidders.add(bidder);
    }
}

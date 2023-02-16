package com.codesquad.autobid.websocket.domain;


import com.codesquad.autobid.auction.repository.Bidder;
import com.codesquad.autobid.user.service.UserService;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class AuctionDtoWebSocket {
    private Long price;
    private Long userNumber;
    private List<BidderDto> bidders = new ArrayList<>();

    public AuctionDtoWebSocket(Long price, List<BidderDto> bidders) {
        this.price = price;
        this.bidders = bidders;
    }


    public static AuctionDtoWebSocket of(Long price, List<BidderDto> bidders) {
        AuctionDtoWebSocket auctionDtoWebSocket =  new AuctionDtoWebSocket(price, bidders);
        auctionDtoWebSocket.setUserNumber( Long.valueOf(bidders.size()));
        return auctionDtoWebSocket;
    }
}

package com.codesquad.autobid.websocket.domain;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    public AuctionDtoWebSocket() {
    }


    public static AuctionDtoWebSocket of(Long price, List<BidderDto> bidders) {
        AuctionDtoWebSocket auctionDtoWebSocket =  new AuctionDtoWebSocket(price, bidders);
        auctionDtoWebSocket.setUserNumber( Long.valueOf(bidders.size()));
        return auctionDtoWebSocket;
    }
}

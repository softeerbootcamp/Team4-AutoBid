package com.codesquad.autobid.websocket.domain;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AuctionDtoWebSocket {
    private Long price;
    private Long numberOfUsers;
    private List<BidderDto> users = new ArrayList<>();

    public AuctionDtoWebSocket(Long price, List<BidderDto> users) {
        this.price = price;
        this.users = users;
    }

    public static AuctionDtoWebSocket of(Long price, List<BidderDto> bidders) {
        AuctionDtoWebSocket auctionDtoWebSocket =  new AuctionDtoWebSocket(price, bidders);
        auctionDtoWebSocket.setNumberOfUsers( Long.valueOf(bidders.size()));
        return auctionDtoWebSocket;
    }
}

package com.codesquad.autobid.websocket.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuctionUserWebSocket {
    Long auctionId;
    String session;

    public AuctionUserWebSocket() {

    }

    public AuctionUserWebSocket(Long auctionId, String session) {
        this.auctionId = auctionId;
        this.session = session;
    }

    public static AuctionUserWebSocket of(Long auctionId, String session) {
        return new AuctionUserWebSocket(auctionId, session);
    }
}

package com.codesquad.autobid.websocket.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuctionWebSocket {
    Long auctionId;
    String session;

    public AuctionWebSocket() {

    }

    public AuctionWebSocket(Long auctionId, String session) {
        this.auctionId = auctionId;
        this.session = session;
    }

    public static AuctionWebSocket of(Long auctionId, String session) {
        return new AuctionWebSocket(auctionId, session);
    }
}

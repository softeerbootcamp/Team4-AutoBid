package com.codesquad.autobid.user.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuctionRoomDto {

    private String auctionId;
    private String userName;


    public AuctionRoomDto(String auctionId, String userName) {
        this.auctionId = auctionId;
        this.userName = userName;
    }

    public AuctionRoomDto() {
    }
}

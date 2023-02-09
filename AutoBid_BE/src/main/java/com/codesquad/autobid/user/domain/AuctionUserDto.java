package com.codesquad.autobid.user.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuctionUserDto {

    private String auctionId;
    private String userName;


    public AuctionUserDto(String auctionId, String userName) {
        this.auctionId = auctionId;
        this.userName = userName;
    }

    public AuctionUserDto() {
    }
}

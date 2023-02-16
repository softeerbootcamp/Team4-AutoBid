package com.codesquad.autobid.websocket.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BidderDto {
    private Long userId;
    private String userName;
    private String mobileNum;
    private Long price;

    public BidderDto(Long userId, String userName, String mobileNum, Long price) {
        this.userId = userId;
        this.userName = userName;
        this.mobileNum = mobileNum;
        this.price = price;
    }

    public BidderDto() {
    }
}

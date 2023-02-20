package com.codesquad.autobid.websocket.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BidderDto {
    private Long userId;
    private String username;
    private String phoneNumber;
    private Long price;

    public BidderDto(Long userId, String userName, String phoneNumber, Long price) {
        this.userId = userId;
        this.username = userName;
        this.phoneNumber = phoneNumber;
        this.price = price;
    }

    public BidderDto() {
    }
}

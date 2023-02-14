package com.codesquad.autobid.auction.repository;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Bidder {

    private Long userId;
    private Long price;

    public Bidder(Long userId, Long price) {
        this.userId = userId;
        this.price = price;
    }
}

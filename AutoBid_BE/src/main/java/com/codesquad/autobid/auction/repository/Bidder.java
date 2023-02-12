package com.codesquad.autobid.auction.repository;

import lombok.Getter;

@Getter
public class Bidder {

    private Long userId;
    private Long price;

    public Bidder(Long userId, Long price) {
        this.userId = userId;
        this.price = price;
    }

    public void bid(Long price) {
        this.price = price;
    }
}

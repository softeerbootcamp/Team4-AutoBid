package com.codesquad.autobid.auction.repository.exceptions;

public class AuctionNotFoundException extends Exception {

    public AuctionNotFoundException() {
        super("auction not found");
    }
}

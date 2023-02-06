package com.codesquad.autobid.auction.domain;

public enum AuctionStatus {

	PROGRESS(0),
	BEFORE(1),
	COMPLETED(2);
	private int status;

	AuctionStatus(int status) {
		this.status = status;
	}
}

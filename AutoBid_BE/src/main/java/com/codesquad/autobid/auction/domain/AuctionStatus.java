package com.codesquad.autobid.auction.domain;

public enum AuctionStatus {

	PROGRESS(0),
	BEFORE(1),
	COMPLETED(2);
	private int status;

	public static final Long BEFORE_END_PRICE = 0L;

	AuctionStatus(int status) {
		this.status = status;
	}
}

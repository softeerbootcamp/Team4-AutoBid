package com.codesquad.autobid.bid.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter @Setter
public class BidRegisterRequest {
	private Long auctionId;
	private Long suggestedPrice;
}

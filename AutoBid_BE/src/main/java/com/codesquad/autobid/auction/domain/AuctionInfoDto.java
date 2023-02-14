package com.codesquad.autobid.auction.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
public class AuctionInfoDto {
	private String auctionTitle;
	private LocalDateTime auctionStartTime;
	private Long auctionId;
	private LocalDateTime auctionEndTime;
	private Long auctionStartPrice;
	private Long auctionEndPrice;
	private String auctionStatus;
	private Long userId;
	private Long carId;
	private Long carDistance;
	private String carName;
	private String carSellname;
	private String carCarid;
	private String carType;
	private String carState;
	private List<String> images;

}

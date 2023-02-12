package com.codesquad.autobid.auction.response;

import java.time.LocalDateTime;
import java.util.List;

import com.codesquad.autobid.auction.domain.AuctionStatus;
import com.codesquad.autobid.car.domain.Type;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class AuctionInfoResponse {

	private Long auctionId;
	private String title;
	private Long price;
	private String name;
	private Long distance;
	private String type;
	private String auctionStatus;
	private List<String> images;
	private LocalDateTime auctionStartTime;
	private LocalDateTime auctionEndTime;

	public AuctionInfoResponse(Long auctionId, String title, Long price, Type carType, Long distance, String kind,
		AuctionStatus auctionStatus, List<String> images, LocalDateTime auctionStartTime,
		LocalDateTime auctionEndTime) {
		this.auctionId = auctionId;
		this.title = title;
		this.price = price;
		this.name = name;
		this.distance = distance;
		this.type = carType.name();
		this.auctionStatus = auctionStatus.name();
		this.images = images;
		this.auctionStartTime = auctionStartTime;
		this.auctionEndTime = auctionEndTime;
	}
}

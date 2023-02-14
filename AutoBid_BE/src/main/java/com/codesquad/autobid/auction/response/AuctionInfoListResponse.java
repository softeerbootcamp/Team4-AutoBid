package com.codesquad.autobid.auction.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.thymeleaf.expression.Lists;

import com.codesquad.autobid.auction.domain.AuctionInfoDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class AuctionInfoListResponse {
	private int totalAuctionNum;
	private List<AuctionInfo> auctionInfoList;

	private AuctionInfoListResponse(int totalAuctionNum, List<AuctionInfo> auctionInfoList) {
		this.totalAuctionNum = totalAuctionNum;
		this.auctionInfoList = auctionInfoList;
	}

	public static AuctionInfoListResponse of(List<AuctionInfoDto> auctionInfoDtoList, int totalAuctionNum) {
		List<AuctionInfo> auctionInfoLists = auctionInfoDtoList.stream().map(auctionInfoDto -> {
			return new AuctionInfo(auctionInfoDto.getAuctionId(), auctionInfoDto.getAuctionTitle(),
				auctionInfoDto.getImages(), auctionInfoDto.getAuctionStartPrice(), auctionInfoDto.getAuctionEndPrice(),
				auctionInfoDto.getAuctionStatus(), auctionInfoDto.getAuctionStartTime(),
				auctionInfoDto.getAuctionEndTime(), new CarInfo(auctionInfoDto.getCarId(),
				auctionInfoDto.getCarDistance(), auctionInfoDto.getCarName(), auctionInfoDto.getCarType(),
				auctionInfoDto.getCarSellname()));
		}).collect(Collectors.toList());

		return new AuctionInfoListResponse(totalAuctionNum, auctionInfoLists);
	}
}

class AuctionInfo {
	private Long auctionId;
	private String title;
	private List<String> images;
	private Long startPrice;
	private Long endPrice;
	private String status;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private CarInfo carInfo;

	public AuctionInfo(Long auctionId, String title, List<String> images, Long startPrice, Long endPrice, String status,
		LocalDateTime startTime, LocalDateTime endTime, CarInfo carInfo) {
		this.auctionId = auctionId;
		this.title = title;
		this.images = images;
		this.startPrice = startPrice;
		this.endPrice = endPrice;
		this.status = status;
		this.startTime = startTime;
		this.endTime = endTime;
		this.carInfo = carInfo;
	}
}

class CarInfo {
	private Long carId;
	private Long distance;
	private String name;
	private String type;
	private String sellName;

	public CarInfo(Long carId, Long distance, String name, String type, String sellName) {
		this.carId = carId;
		this.distance = distance;
		this.name = name;
		this.type = type;
		this.sellName = sellName;
	}
}


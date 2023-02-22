package com.codesquad.autobid.auction.response;

import com.codesquad.autobid.auction.domain.AuctionInfoDto;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ToString

@Getter @Setter
@NoArgsConstructor
public class AuctionInfoListResponse {
    private int totalAuctionNum;
    private List<AuctionInfo> auctionInfoList;

    private AuctionInfoListResponse(int totalAuctionNum, List<AuctionInfo> auctionInfoList) {
        this.totalAuctionNum = totalAuctionNum;
        this.auctionInfoList = auctionInfoList;
    }

    public static AuctionInfoListResponse of(List<AuctionInfoDto> auctionInfoDtoList, int totalAuctionNum) {
        List<AuctionInfo> auctionInfoLists = auctionInfoDtoList.stream().map(auctionInfoDto ->
            new AuctionInfo(auctionInfoDto.getAuctionId(), auctionInfoDto.getAuctionTitle(),
                auctionInfoDto.getImages(), auctionInfoDto.getAuctionStartPrice(), auctionInfoDto.getAuctionEndPrice(),
                auctionInfoDto.getAuctionStatus(), auctionInfoDto.getAuctionStartTime(),
                auctionInfoDto.getAuctionEndTime(), new CarInfo(auctionInfoDto.getCarState(), auctionInfoDto.getCarId(),
                auctionInfoDto.getCarDistance(), auctionInfoDto.getCarName(), auctionInfoDto.getCarType(),
                auctionInfoDto.getCarSellname()))
        ).collect(Collectors.toList());

        return new AuctionInfoListResponse(totalAuctionNum, auctionInfoLists);
    }

    public boolean empty() {
        return totalAuctionNum == 0;
    }
}

@ToString
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Getter @Setter
@NoArgsConstructor
class AuctionInfo implements Serializable {

	private Long auctionId;
	private String title;
	private List<String> images;
	private Long startPrice;
	private Long endPrice;
	private String status;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime startTime;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
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

@ToString
@Getter @Setter
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class CarInfo implements Serializable {
    private Long carId;
    private Long distance;
    private String name;
    private String type;
    private String sellName;
    private String state;

    public CarInfo(String state, Long carId, Long distance, String name, String type, String sellName) {
        this.carId = carId;
        this.distance = distance;
        this.name = name;
        this.type = type;
        this.sellName = sellName;
        this.state = state;
    }
}


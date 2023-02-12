package com.codesquad.autobid.auction.request;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class AuctionRegisterRequest {
	@Schema(description = "이미지 리스트")
	private List<MultipartFile> multipartFileList;
	@Schema(example = "5", description = "자동차 아이디")
	private Long carId;
	@Schema(example = "2022-12-23 12:23", description = "경매 시작 시간")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime auctionStartTime;
	@Schema(example = "2022-12-23 12:23", description = "경매 종료 시간")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime auctionEndTime;
	@Schema(example = "1000", description = "경매 시작 가격")
	private Long auctionStartPrice;

}

package com.codesquad.autobid.auction.request;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Getter
public class AuctionRegisterRequest {
	private List<MultipartFile> multipartFileList;
	private Long carId;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime auctionStartTime;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime auctionEndTime;
	private Long auctionStartPrice;


}

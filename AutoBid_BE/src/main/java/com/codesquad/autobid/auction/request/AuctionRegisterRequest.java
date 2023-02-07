package com.codesquad.autobid.auction.request;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class AuctionRegisterRequest {
	@Schema(description = "")
	private List<MultipartFile> multipartFileList;
	@Schema
	private Long carId;
	@Schema
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime auctionStartTime;
	@Schema
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime auctionEndTime;
	@Schema
	private Long auctionStartPrice;

}

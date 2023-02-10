package com.codesquad.autobid.auction.controller;

import com.codesquad.autobid.user.domain.AuctionRoomDto;
import com.codesquad.autobid.user.domain.EnterUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import com.codesquad.autobid.auction.request.AuctionRegisterRequest;
import com.codesquad.autobid.auction.service.AuctionService;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.web.argumentresolver.AuthorizedUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Auction", description = "경매 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auction")
public class AuctionController {

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	public final AuctionService auctionService;

	@Operation(summary = "경매 생성 API", description = "경매를 등록한다.")
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void auctionAdd(@Parameter @ModelAttribute AuctionRegisterRequest auctionRegisterRequest,
		@Parameter(hidden = true) @AuthorizedUser User user) {

		log.debug("POST /auction request : {}", auctionRegisterRequest);
		auctionService.addAuction(auctionRegisterRequest, user);
	}

	@MessageMapping("/createRoom") // 클라이언트에서 보내는 메세지 매핑
	public AuctionRoomDto createRoom(@Payload AuctionRoomDto auctionRoomDto) {
		log.info("auction 방 번호 : {} 생성", auctionRoomDto.getAuctionId());
		messagingTemplate.convertAndSend("/subscribe/auction/room/" + auctionRoomDto.getAuctionId(), auctionRoomDto);
		return auctionRoomDto;
	}

	@MessageMapping("/enterRoom/{roomnum}") // 클라이언트에서 보내는 메세지 매핑
	public EnterUserDto enterRoom(@DestinationVariable(value = "roomnum") String roomNum, @Payload EnterUserDto enterUserDto) {
		log.info("{} 님이 {} 번호님이 {}번 방에 입장", enterUserDto.getUserName(), enterUserDto.getMobileNum(), roomNum);
		messagingTemplate.convertAndSend("/subscribe/auction/room/" + enterUserDto.getRoomNum(), enterUserDto);
		// 해당 방으로 입장한다는 메세지
		return enterUserDto;
	}

}

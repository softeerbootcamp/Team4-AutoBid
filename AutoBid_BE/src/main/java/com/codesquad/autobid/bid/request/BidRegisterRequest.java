package com.codesquad.autobid.bid.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Schema(description = "입찰 RequestBody")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BidRegisterRequest {
	@Schema(description = "경매 ID", example = "5")
	private Long auctionId;
	@Schema(description = "제안 가격", example = "5000")
	private Long suggestedPrice;
}

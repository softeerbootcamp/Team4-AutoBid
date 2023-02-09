package com.codesquad.autobid.bid.service;

import javax.swing.text.StyledEditorKit;

import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

import com.codesquad.autobid.bid.domain.Bid;
import com.codesquad.autobid.bid.repository.BidRepository;
import com.codesquad.autobid.bid.request.BidRegisterRequest;
import com.codesquad.autobid.user.domain.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BidService {

	private final BidRepository bidRepository;

	public Boolean suggestBid(BidRegisterRequest bidRegisterRequest, User user) {
		if (!checkBid(bidRegisterRequest.getSuggestedPrice(), bidRegisterRequest.getSuggestedPrice(), user)) {
			return false;
		}
		Bid bid = Bid.of(AggregateReference.to(bidRegisterRequest.getAuctionId()), AggregateReference.to(user.getId()),
			bidRegisterRequest.getSuggestedPrice(), false);
		bidRepository.save(bid);

		return true;
	}

	// 입찰 가능한지 확인
	// 동시성 해결 예정
	public Boolean checkBid(Long price, Long auctionId, User User) {
		return true;
	}
}


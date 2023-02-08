package com.codesquad.autobid.bid.service;

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

	public void addBid(BidRegisterRequest bidRegisterRequest, User user) {
		//Bid bid = Bid.of(AggregateReference.to(bidRegisterRequest.getAuctionId()), user.getId(), )
		//bidRepository.save()
	}
}

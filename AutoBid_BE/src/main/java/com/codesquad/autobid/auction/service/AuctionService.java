package com.codesquad.autobid.auction.service;

import java.io.IOException;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.auction.domain.AuctionStatus;
import com.codesquad.autobid.auction.repository.AuctionRepository;
import com.codesquad.autobid.auction.request.AuctionRegisterRequest;
import com.codesquad.autobid.image.domain.Image;
import com.codesquad.autobid.image.repository.ImageRepository;
import com.codesquad.autobid.image.service.S3Uploader;
import com.codesquad.autobid.user.domain.User;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AuctionService {

	private final S3Uploader s3Uploader;
	private final AuctionRepository auctionRepository;
	private final ImageRepository imageRepository;

    @Transactional
	public void addAuction(AuctionRegisterRequest auctionRegisterRequest, User user) {

		Auction auction = Auction.of(auctionRegisterRequest.getCarId(), user.getId(),
			auctionRegisterRequest.getAuctionStartTime(),
			auctionRegisterRequest.getAuctionEndTime(), auctionRegisterRequest.getAuctionStartPrice(),
			AuctionStatus.BEFORE_END_PRICE, AuctionStatus.BEFORE);

		auctionRepository.save(auction);

		addImageList(auctionRegisterRequest.getMultipartFileList(), auction.getId());

	}

	@Transactional
	public void addImageList(List<MultipartFile> multipartFiles, Long auctionId) {

		for (MultipartFile multipartFile : multipartFiles) {
			try {
				String imageUrl = s3Uploader.upload(multipartFile);
				imageRepository.save(Image.of(auctionId, imageUrl));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}

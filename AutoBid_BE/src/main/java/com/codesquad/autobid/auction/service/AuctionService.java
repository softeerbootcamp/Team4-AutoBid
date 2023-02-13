package com.codesquad.autobid.auction.service;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.auction.domain.AuctionStatus;
import com.codesquad.autobid.auction.repository.AuctionRedis;
import com.codesquad.autobid.auction.repository.AuctionRedisRepository;
import com.codesquad.autobid.auction.repository.AuctionRepository;
import com.codesquad.autobid.auction.request.AuctionRegisterRequest;
import com.codesquad.autobid.image.domain.Image;
import com.codesquad.autobid.image.repository.ImageRepository;
import com.codesquad.autobid.image.service.S3Uploader;
import com.codesquad.autobid.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AuctionService {

    private final S3Uploader s3Uploader;
    private final AuctionRepository auctionRepository;
    private final AuctionRedisRepository auctionRedisRepository;
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

    @Transactional
    public void openPendingAuctions(LocalDateTime openTime) {
        List<Auction> auctions = auctionRepository.getAuctionByAuctionStatusAndAuctionStartTime(AuctionStatus.BEFORE, openTime);
        for (Auction auction : auctions) {
            openAuction(auction);
            // socketHandler.openSocket(auction);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void openAuction(Auction auction) {
        // redis, mysql이 같은 transaction으로 처리되는지 확인해야 함
        auction.openAuction();
        auctionRepository.save(auction);
        auctionRedisRepository.save(AuctionRedis.from(auction));
    }

    @Transactional
    public void closeFulfilledAuctions(LocalDateTime closeTime) {
        List<Auction> auctions = auctionRepository.getAuctionByAuctionStatusAndAuctionEndTime(AuctionStatus.BEFORE, closeTime);
        for (Auction auction : auctions) {
            closeAuction(auction);
            // socketHandler.closeSocket(auction);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void closeAuction(Auction auction) {
        auction.closeAuction();
        auctionRedisRepository.delete(auction);
        auctionRepository.save(auction);
    }
}

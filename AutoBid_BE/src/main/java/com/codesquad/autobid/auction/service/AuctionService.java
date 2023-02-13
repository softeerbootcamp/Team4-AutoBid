package com.codesquad.autobid.auction.service;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.auction.domain.AuctionStatus;
import com.codesquad.autobid.auction.repository.AuctionRedis;
import com.codesquad.autobid.auction.repository.AuctionRedisRepository;
import com.codesquad.autobid.auction.repository.AuctionRepository;
import com.codesquad.autobid.auction.repository.Bidder;
import com.codesquad.autobid.auction.request.AuctionRegisterRequest;
import com.codesquad.autobid.email.EmailService;
import com.codesquad.autobid.image.domain.Image;
import com.codesquad.autobid.image.repository.ImageRepository;
import com.codesquad.autobid.image.service.S3Uploader;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AuctionService {

    private final S3Uploader s3Uploader;
    private final AuctionRepository auctionRepository;
    private final AuctionRedisRepository auctionRedisRepository;
    private final ImageRepository imageRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;

    @Transactional
    public void addAuction(AuctionRegisterRequest auctionRegisterRequest, User user) {
        Auction auction = Auction.of(auctionRegisterRequest.getCarId(), user.getId(),
                auctionRegisterRequest.getAuctionStartTime(),
                auctionRegisterRequest.getAuctionEndTime(), auctionRegisterRequest.getAuctionStartPrice(),
                AuctionStatus.BEFORE_END_PRICE, AuctionStatus.BEFORE);
        auctionRepository.save(auction);
        List<MultipartFile> images = auctionRegisterRequest.getMultipartFileList();
        for (MultipartFile image : images) {
            saveImage(image, auction.getId());
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveImage(MultipartFile image, Long auctionId) {
        try {
            String imageUrl = s3Uploader.upload(image);
            imageRepository.save(Image.of(auctionId, imageUrl));
        } catch (IOException e) {
            throw new RuntimeException(e);
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
            Set<Bidder> bidders = closeAuction(auction);
            // todo: socket 연결
            // socketHandler.closeSocket(auction);
            // todo: kafka 적용 예정
            bidders.stream().forEach((bidder) -> {
                User bidOwner = userRepository.findById(bidder.getUserId()).get();
                emailService.send(auction, bidOwner, bidder.getPrice());
            });
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Set<Bidder> closeAuction(Auction auction) {
        AuctionRedis findAuction = auctionRedisRepository.findById(auction.getId());
        updateAuction(auction, findAuction);
        auctionRepository.save(auction);
        auctionRedisRepository.delete(auction);
        return findAuction.getBidders();
    }

    private void updateAuction(Auction auction, AuctionRedis findAuction) {
        auction.closeAuction();
    }
}

package com.codesquad.autobid.auction.service;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.auction.domain.AuctionInfoDto;
import com.codesquad.autobid.auction.domain.AuctionStatus;
import com.codesquad.autobid.auction.repository.AuctionRedis;
import com.codesquad.autobid.auction.repository.AuctionRedisRepository;
import com.codesquad.autobid.auction.repository.AuctionRepository;
import com.codesquad.autobid.auction.repository.Bidder;
import com.codesquad.autobid.auction.request.AuctionRegisterRequest;
import com.codesquad.autobid.auction.response.AuctionInfoListResponse;
import com.codesquad.autobid.email.EmailService;
import com.codesquad.autobid.image.domain.Image;
import com.codesquad.autobid.image.repository.ImageRepository;
import com.codesquad.autobid.image.service.S3Uploader;
import com.codesquad.autobid.kafka.adapter.AuctionSaveAdapter;
import com.codesquad.autobid.kafka.producer.AuctionOpenProducer;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AuctionService {

    private final S3Uploader s3Uploader;
    private final AuctionRepository auctionRepository;
    private final ImageRepository imageRepository;
    private final AuctionRedisRepository auctionRedisRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final AuctionOpenProducer auctionOpenProducer;
    private final AuctionSaveAdapter auctionSaveAdapter;


    @Transactional
    public Auction addAuction(AuctionRegisterRequest auctionRegisterRequest, User user) {
        Auction auction = Auction.of(auctionRegisterRequest.getCarId(), user.getId(),
            auctionRegisterRequest.getAuctionTitle(),
            auctionRegisterRequest.getAuctionStartTime(),
            auctionRegisterRequest.getAuctionEndTime(), auctionRegisterRequest.getAuctionStartPrice(),
            AuctionStatus.BEFORE_END_PRICE, AuctionStatus.BEFORE);
        auctionRepository.save(auction);
        List<MultipartFile> images = auctionRegisterRequest.getMultipartFileList();
        for (MultipartFile image : images) {
            saveImage(image, auction.getId());
        }
        return auction;
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

    public void openPendingAuctions(LocalDateTime openTime) {
        List<Auction> auctions = auctionRepository.getAuctionByAuctionStatusAndAuctionStartTime(AuctionStatus.BEFORE,
            openTime);
        auctionOpenProducer.produce(auctions);  // -> topic name: upload-auction-redis
    }

    @Transactional
    public void closeFulfilledAuctions(LocalDateTime closeTime) {
        List<Auction> auctions = auctionRepository.getAuctionByAuctionStatusAndAuctionEndTime(AuctionStatus.PROGRESS,
            closeTime);
        emailProducer.produceMessage();
        for (Auction auction : auctions) {
            Set<Bidder> bidders = closeAuction(auction);
            // todo: socket close
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
        AuctionRedis auctionRedis = auctionRedisRepository.findById(auction.getId());
        auction.update(auctionRedis);
        auctionRepository.save(auction);
        auctionRedisRepository.delete(auction);
        return auctionRedis.getBidders();
    }

    public AuctionInfoListResponse getAuctions(String carType, String auctionStatus, Long startPrice, Long endPrice,
                                               int page, int size) {
        List<AuctionInfoDto> auctionInfoDtoList = getAuctionDtoList(carType, auctionStatus, startPrice, endPrice);
        System.out.println(auctionInfoDtoList);
        return getAuctionInfoListResponse(auctionInfoDtoList, page, size);
    }

    public List<AuctionInfoDto> getAuctionDtoList(String carType, String auctionStatus, Long startPrice,
                                                  Long endPrice) {
        List<AuctionInfoDto> auctionInfoDtoList;

        if (carType.equals("ALL") && auctionStatus.equals("ALL")) { // 둘 다 ALL인 경우
            auctionInfoDtoList = auctionRepository.findAllByFilter(startPrice, endPrice);
        } else if (carType.equals("ALL")) { // carType만 ALL인 경우
            auctionInfoDtoList = auctionRepository.findAllByFilterWithAuctionStatus(startPrice, endPrice,
                auctionStatus);
        } else if (auctionStatus.equals("ALL")) { // auctionStatus만 ALL인 경우
            auctionInfoDtoList = auctionRepository.findAllByFilterWithCarType(startPrice, endPrice, carType);
        } else { // 둘 다 ALL 아닌 경우
            auctionInfoDtoList = auctionRepository.findAllByFilterWithAuctionStatusAndCarType(startPrice, endPrice,
                auctionStatus, carType);
        }

        return auctionInfoDtoList;
    }

    // 1 0~4
    // 2 5 ~ 9 (size-1)*page ~ size*page -1
    // 3 10 ~ 14
    public AuctionInfoListResponse getAuctionInfoListResponse(List<AuctionInfoDto> auctionInfoDtoList, int page,
                                                              int size) {
        int totalAuctionNum = auctionInfoDtoList.size();
        if (totalAuctionNum < page * size - 1) {
            auctionInfoDtoList.subList((size - 1) * page, totalAuctionNum - 1);
        } else {
            auctionInfoDtoList.subList((size - 1) * page, (size) * page - 1);
        }
        auctionInfoDtoList.forEach(auctionInfoDto -> {
            List<Image> images = imageRepository.findAllByAuctionId(
                AggregateReference.to(auctionInfoDto.getAuctionId()));
            auctionInfoDto.setImages(images.stream().map(Image::getImageUrl).collect(Collectors.toList()));
        });

        return AuctionInfoListResponse.of(auctionInfoDtoList, totalAuctionNum);
    }
}

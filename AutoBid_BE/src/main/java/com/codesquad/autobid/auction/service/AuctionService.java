package com.codesquad.autobid.auction.service;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.auction.domain.AuctionInfoDto;
import com.codesquad.autobid.auction.domain.AuctionStatus;
import com.codesquad.autobid.auction.repository.AuctionRedisDTO;
import com.codesquad.autobid.auction.repository.AuctionRedisRepository;
import com.codesquad.autobid.auction.repository.AuctionRepository;
import com.codesquad.autobid.auction.repository.exceptions.BidSaveFailedException;
import com.codesquad.autobid.auction.request.AuctionRegisterRequest;
import com.codesquad.autobid.auction.response.AuctionInfoListResponse;
import com.codesquad.autobid.auction.response.AuctionStatisticsResponse;
import com.codesquad.autobid.bid.domain.Bid;
import com.codesquad.autobid.bid.kafka.BidAdapter;
import com.codesquad.autobid.bid.kafka.BidRollbackProducer;
import com.codesquad.autobid.bid.request.BidRegisterRequest;
import com.codesquad.autobid.image.domain.Image;
import com.codesquad.autobid.image.repository.ImageRepository;
import com.codesquad.autobid.image.service.S3Uploader;
import com.codesquad.autobid.kafka.producer.AuctionCloseProducer;
import com.codesquad.autobid.kafka.producer.AuctionOpenProducer;
import com.codesquad.autobid.kafka.producer.dto.AuctionKafkaDTO;
import com.codesquad.autobid.user.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class AuctionService {

    private static final String ALL = "ALL";
    private final S3Uploader s3Uploader;
    private final AuctionRepository auctionRepository;
    private final ImageRepository imageRepository;
    private final AuctionRedisRepository auctionRedisRepository;
    private final AuctionOpenProducer auctionOpenProducer;
    private final AuctionCloseProducer auctionCloseProducer;
    private final BidAdapter bidAdapter;
    private final BidRollbackProducer bidRollbackProducer;

    @Transactional
    public boolean saveBid(BidRegisterRequest bidRegisterRequest) {
        try {
            boolean result = auctionRedisRepository.saveBid(
                    Bid.of(
                            AggregateReference.to(bidRegisterRequest.getAuctionId()),
                            AggregateReference.to(bidRegisterRequest.getUserId()),
                            bidRegisterRequest.getSuggestedPrice(),
                            false
                    )
            );
            if (result) {
                bidAdapter.produce(bidRegisterRequest);
            }

            return result;
        } catch (BidSaveFailedException e) {
            bidRollbackProducer.produce(bidRegisterRequest);
        }
        return false;
    }

    @Transactional
    public Auction addAuction(AuctionRegisterRequest auctionRegisterRequest, User user) {
        Auction auction = Auction.of(
                auctionRegisterRequest.getCarId(),
                user.getId(),
                auctionRegisterRequest.getAuctionTitle(),
                auctionRegisterRequest.getAuctionStartTime(),
                auctionRegisterRequest.getAuctionEndTime(),
                auctionRegisterRequest.getAuctionStartPrice(),
                auctionRegisterRequest.getAuctionStartPrice(),
                AuctionStatus.BEFORE
        );
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

    public void openPendingAuctions(LocalDateTime openTime) throws JsonProcessingException {
        List<Auction> auctions = auctionRepository.getAuctionByAuctionStatusAndAuctionStartTime(AuctionStatus.BEFORE, openTime);
        auctionOpenProducer.produce(parseToAuctionKafkaDTO(auctions));
    }

    public void closeFulfilledAuctions(LocalDateTime closeTime) throws JsonProcessingException {
        List<Auction> auctions = auctionRepository.getAuctionByAuctionStatusAndAuctionEndTime(AuctionStatus.PROGRESS, closeTime);
        auctionCloseProducer.produce(parseToAuctionKafkaDTO(auctions));
    }

    private List<AuctionKafkaDTO> parseToAuctionKafkaDTO(List<Auction> auctions) {
        return auctions.stream().map(AuctionKafkaDTO::from).collect(Collectors.toList());
    }

    @Cacheable(value = "auction", key = "#page+#carType+#auctionStatus+#startPrice+#endPrice+#size")
    public AuctionInfoListResponse getAuctions(String carType, String auctionStatus, Long startPrice, Long endPrice, int page, int size) {
        List<AuctionInfoDto> auctionInfoDtoList = getAuctionDtoList(carType, auctionStatus, startPrice, endPrice);
        return getAuctionInfoListResponse(auctionInfoDtoList, page, size);
    }

    public List<AuctionInfoDto> getAuctionDtoList(String carType, String auctionStatus, Long startPrice, Long endPrice) {
        List<AuctionInfoDto> auctionInfoDtoList;

        if (carType.equals(ALL) && auctionStatus.equals(ALL)) { // 둘 다 ALL인 경우
            auctionInfoDtoList = auctionRepository.findAllByFilter(startPrice, endPrice);
        } else if (carType.equals(ALL)) { // carType만 ALL인 경우
            auctionInfoDtoList = auctionRepository.findAllByFilterWithAuctionStatus(startPrice, endPrice, auctionStatus);
        } else if (auctionStatus.equals(ALL)) { // auctionStatus만 ALL인 경우
            auctionInfoDtoList = auctionRepository.findAllByFilterWithCarType(startPrice, endPrice, carType);
        } else { // 둘 다 ALL 아닌 경우
            auctionInfoDtoList = auctionRepository.findAllByFilterWithAuctionStatusAndCarType(startPrice, endPrice, auctionStatus, carType);
        }

        return auctionInfoDtoList;
    }

    public AuctionInfoListResponse getAuctionInfoListResponse(List<AuctionInfoDto> auctionInfoDtoList, int page, int size) {
        int totalAuctionNum = auctionInfoDtoList.size();
        auctionInfoDtoList = subAuctionDtoList(auctionInfoDtoList, page, size, totalAuctionNum);

        return auctionInfoDtoListToAuctionInfoListResponse(auctionInfoDtoList, totalAuctionNum);
    }

    public AuctionInfoListResponse auctionInfoDtoListToAuctionInfoListResponse(List<AuctionInfoDto> auctionInfoDtoList, int totalAuctionNum) {
        auctionInfoDtoList.forEach(auctionInfoDto -> {
            List<Image> images = imageRepository.findAllByAuctionId(AggregateReference.to(auctionInfoDto.getAuctionId()));
            auctionInfoDto.setImages(images.stream().map(Image::getImageUrl).collect(Collectors.toList()));
        });

        return AuctionInfoListResponse.of(auctionInfoDtoList, totalAuctionNum);
    }

    public List<AuctionInfoDto> subAuctionDtoList(List<AuctionInfoDto> auctionInfoDtoList, int page, int size, int totalAuctionNum) {
        if (totalAuctionNum == 0) {
            return auctionInfoDtoList;
        }

        if (totalAuctionNum < page * size) {
            auctionInfoDtoList = auctionInfoDtoList.subList(size * (page - 1), totalAuctionNum);
        } else {
            auctionInfoDtoList = auctionInfoDtoList.subList(size * (page - 1), size * page);
        }

        return auctionInfoDtoList;
    }

    @Cacheable(value = "auction_stat", key = "#carType+#auctionStatus")
    public AuctionStatisticsResponse getAuctionStaticsResponse(String carType, String auctionStatus) {
        List<Long> auctionInfoDtoList = getAuctionInfoDtoForStatistics(carType, auctionStatus);
        int[] contents = init();

        int totalSold = auctionRepository.countAllByAuctionStatus(AuctionStatus.COMPLETED);
        if (auctionInfoDtoList.size() == 0) {
            return AuctionStatisticsResponse.of(0, 0L, 0L, contents);
        }
        Long minPrice = auctionInfoDtoList.get(0);
        Long maxPrice = auctionInfoDtoList.get(auctionInfoDtoList.size() - 1);
        if (maxPrice - minPrice <= 30) {
            maxPrice = minPrice + 100;
        }
        long intervalPrice = (maxPrice - minPrice) / 20;

        auctionInfoDtoList.forEach(auctionInfoDto -> {
            long idx = Math.floorDiv((auctionInfoDto - minPrice), intervalPrice);
            if (idx == 20) {
                contents[19] += 1;
            } else {
                contents[Math.toIntExact(idx)] += 1;
            }

        });
        return AuctionStatisticsResponse.of(totalSold, minPrice, maxPrice, contents);
    }

    private int[] init() {
        int[] ints = new int[20];
        Arrays.fill(ints, 0);
        return ints;
    }


    public List<Long> getAuctionInfoDtoForStatistics(String carType, String auctionStatus) {
        List<Long> auctionInfoDtoList;
        if (carType.equals(ALL) && auctionStatus.equals(ALL)) {
            auctionInfoDtoList = auctionRepository.findAllForStatistics();
        } else if (carType.equals(ALL)) {
            auctionInfoDtoList = auctionRepository.findAllByAuctionStatus(auctionStatus);
        } else if (auctionStatus.equals(ALL)) {
            auctionInfoDtoList = auctionRepository.findAllByCarType(carType);
        } else {
            auctionInfoDtoList = auctionRepository.findAllByAuctionStatusAndCarType(auctionStatus, carType);
        }

        return auctionInfoDtoList;
    }

    public AuctionInfoListResponse getMyAuctions(User user) {
        List<AuctionInfoDto> auctionInfoDtoList = auctionRepository.findAllByUserId(user.getId());
        return auctionInfoDtoListToAuctionInfoListResponse(auctionInfoDtoList, auctionInfoDtoList.size());
    }

    public AuctionInfoListResponse getMyParticipatingAuctions(User user) {
        List<AuctionInfoDto> auctionInfoDtoList = auctionRepository.findAllParticipatingAuctions(user.getId());
        return auctionInfoDtoListToAuctionInfoListResponse(auctionInfoDtoList, auctionInfoDtoList.size());
    }

    public AuctionRedisDTO findAuctionByIdFromRedis(Long auctionId) {
        return auctionRedisRepository.findById(auctionId);
    }

    public Auction findAuctionByIdFromDB(Long auctionId) {
        Optional<Auction> auction = auctionRepository.findById(auctionId);
        return auction.orElse(null);
    }
}

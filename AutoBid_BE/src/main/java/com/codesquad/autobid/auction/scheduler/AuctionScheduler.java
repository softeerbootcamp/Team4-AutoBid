package com.codesquad.autobid.auction.scheduler;

import com.codesquad.autobid.auction.service.AuctionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class AuctionScheduler {

    private static final String CRON_RATE = "* */15 * * * *";

    private final AuctionService auctionService;

    @Autowired
    public AuctionScheduler(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @Async
    @Scheduled(cron = CRON_RATE)
    @SchedulerLock(name = "openPendingAuctionsLock")
    public void openPendingAuctions() {
        log.info("open Pending Auctions, {}", getPresentTime());
        try {
            auctionService.openPendingAuctions(getPresentTime());
        } catch (JsonProcessingException e) {
            log.info("openPendingAuctions failed");
        }
    }

    @Async
    @Scheduled(cron = CRON_RATE)
    @SchedulerLock(name = "closeInProgressAuctionsLock")
    public void closeInProgressAuctions() {
        log.info("close InProgress Auctions, {}", getPresentTime());
        try {
            auctionService.closeFulfilledAuctions(getPresentTime());
        } catch (JsonProcessingException e) {
            log.info("closeInProgressAuctions failed");
        }
    }

    private LocalDateTime getPresentTime() {
        LocalDateTime now = LocalDateTime.now();
        return LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(), now.getMinute());
    }
}

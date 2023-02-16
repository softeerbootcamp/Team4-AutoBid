package com.codesquad.autobid.auction.scheduler;

import com.codesquad.autobid.auction.service.AuctionService;
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

    private static final String CRON_RATE = "0 */1 * * * *";

    private final AuctionService auctionService;

    @Autowired
    public AuctionScheduler(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @Async
    @Scheduled(cron = CRON_RATE)
    @SchedulerLock(name = "openPendingAuctionsLock")
    public void openPendingAuctions() {
        log.info("open Pending Auctions");
        auctionService.openPendingAuctions(getPresentTime());
    }

    @Async
    @Scheduled(cron = CRON_RATE)
    @SchedulerLock(name = "closeInProgressAuctionsLock")
    public void closeInProgressAuctions() {
        log.info("close InProgress Auctions");
        auctionService.closeFulfilledAuctions(getPresentTime());
    }

    private LocalDateTime getPresentTime() {
        LocalDateTime now = LocalDateTime.now();
        return LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(), now.getMinute());
    }
}

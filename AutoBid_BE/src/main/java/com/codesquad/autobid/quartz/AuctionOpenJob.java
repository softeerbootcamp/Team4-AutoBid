package com.codesquad.autobid.quartz;

import com.codesquad.autobid.auction.service.AuctionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class AuctionOpenJob implements Job {

    private final AuctionService auctionService;

    @Autowired
    public AuctionOpenJob(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("auction open job executed");
        log.info(context.getJobDetail().getDescription());

        try {
            auctionService.openPendingAuctions(LocalDateTime.now());
        } catch (JsonProcessingException e) {
            log.error("openPendingAuctions failed");
        }

        log.info("Auction open job finish");
    }
}
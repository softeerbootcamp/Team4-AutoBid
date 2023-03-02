package com.codesquad.autobid.quartz;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class QuartzService {

    private final Scheduler scheduler;

    @PostConstruct
    public void init() {
        try {
            scheduler.clear();
            scheduler.getListenerManager().addJobListener(new QuartzJobListener());
            scheduler.getListenerManager().addTriggerListener(new QuartzTriggerListener());
            addJob(AuctionOpenJob.class, "AuctionOpenJob", "Auction Open Job",
                new HashMap<String, String>(), "0 */1 * * * *");
            addJob(AuctionCloseJob.class, "AuctionCloseJob", "Auction Close Job",
                new HashMap<String, String>(), "0 */1 * * * *");
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends Job> void addJob(Class<? extends Job> job, String name, String desc,
        Map paramsMap, String cron) throws SchedulerException {
        JobDetail jobDetail = buildJobDetail(job, name, desc, paramsMap);
        Trigger trigger = buildCronTrigger(cron);
        if (scheduler.checkExists(jobDetail.getKey())) {
            scheduler.deleteJob(jobDetail.getKey());
        }
        scheduler.scheduleJob(jobDetail, trigger);
    }

    public <T extends Job> JobDetail buildJobDetail(Class<? extends Job> job, String name,
        String desc, Map paramsMap) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(paramsMap);

        return JobBuilder
            .newJob(job)
            .withIdentity(name, "group")
            .withDescription(desc)
            .usingJobData(jobDataMap)
            .build();
    }

    private Trigger buildCronTrigger(String cronExp) {
        return TriggerBuilder.newTrigger()
            .withSchedule(CronScheduleBuilder.cronSchedule(cronExp))
            .build();
    }

}

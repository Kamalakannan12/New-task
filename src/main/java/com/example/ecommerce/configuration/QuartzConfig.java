package com.example.ecommerce.configuration;

import com.example.ecommerce.schedular.InactiveUserJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(InactiveUserJob.class)
                .withIdentity("inactiveUserJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger trigger() {
        LocalDateTime now = LocalDateTime.now();

        // Start date
        Date startDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

        // End date
        Date endDate = Date.from(
                now.plusDays(5)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withIdentity("inactiveUserTrigger")
                .startAt(startDate)     // start now
                .endAt(endDate)
               // .withSchedule(CronScheduleBuilder.cronSchedule("* * * * * ?"))
                .build();
    }
}

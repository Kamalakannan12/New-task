package com.example.ecommerce.schedular;

import com.example.ecommerce.entity.UserDetails;
import com.example.ecommerce.service.EmailService;
import com.example.ecommerce.service.UserService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class InactiveUserJob implements Job {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;
    private static final Logger logger = LoggerFactory.getLogger(InactiveUserJob.class);

    @Override
    public void execute(JobExecutionContext context) {

        List<UserDetails> inactiveUsers = userService.getInactiveUsers(1);

        for (UserDetails user : inactiveUsers) {
            emailService.sendSimpleMail(user.getEmail());
        }

        logger.info("Offer emails sent to inactive users");
    }
}

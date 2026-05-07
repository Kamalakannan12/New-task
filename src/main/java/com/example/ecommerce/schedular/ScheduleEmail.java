package com.example.ecommerce.schedular;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.entity.UserDetails;

import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.EmailService;
import com.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.*;
import java.util.List;

@Component
public class ScheduleEmail {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;
//    @Autowired
//    private TaskScheduler taskScheduler;
    private static final Logger logger = LoggerFactory.getLogger(ScheduleEmail.class);


    // Run at 10 AM and 5 PM
//    @Scheduled(fixedRate = 50000)
//    public void sendOfferMail() {
//
//        LocalDate targetDate = LocalDate.of(2026, 4, 29);
//
//        // Today date
//        LocalDate today = LocalDate.now();
//
//        //If not target date → stop
//        if (!today.equals(targetDate)) {
//            return;
//        }
//
//        // Get inactive users
//        List<UserDetails> users = userService.getInactiveUsers(0);
//
//        for (UserDetails user : users) {
//
//            String email = user.getEmail();
//
//            // Send email
//            emailService.sendSimpleMail(email);
//
//            logger.info("Email sent to:{}" , email);
//        }
//    }


}

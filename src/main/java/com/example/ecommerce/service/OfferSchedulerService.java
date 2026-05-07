package com.example.ecommerce.service;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.entity.UserDetails;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.entity.Product;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class OfferSchedulerService {

    private final TaskScheduler taskScheduler;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public OfferSchedulerService(TaskScheduler taskScheduler,
                          ProductRepository productRepository,
                          UserRepository userRepository,
                          EmailService emailService) {
        this.taskScheduler = taskScheduler;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }
    private static final Logger logger = LoggerFactory.getLogger(OfferSchedulerService.class);

    public void scheduleOffer(Long productId, int seconds) {

        LocalDateTime runTime = LocalDateTime.now().plusSeconds(seconds);

        Date date = Date.from(
                runTime.atZone(ZoneId.systemDefault()).toInstant()
        );

        taskScheduler.schedule(() -> {

            // Fetch product
            Product product = productRepository.findById(productId)
                    .orElseThrow();

            // Add offer
            product.setOffer("50% OFF LIMITED TIME");
            productRepository.save(product);

            //  Fetch all users
            List<UserDetails> users = userRepository.findAll();

            //  Send email to each user
            for (UserDetails user : users) {
                emailService.sendEmail(
                        user.getEmail(),
                        "🔥 Special Offer on " + product.getName(),
                        "Dear " + user.getName() +
                                ",\n\nWe have a special offer: " +
                                product.getOffer()
                );
            }

            System.out.println("Offer applied and emails sent!");

        }, date);
    }
        public void scheduleOffer(Long productId, String dateStr, String timeStr,int days) {

            LocalDate date = LocalDate.parse(dateStr);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
            LocalTime time = LocalTime.parse(timeStr, formatter);
            for (int i = 0; i < days; i++) {

                LocalDate nextDate = date.plusDays(i);
                LocalDateTime runTime = LocalDateTime.of(nextDate, time);

                // Skip past time
                if (runTime.isBefore(LocalDateTime.now())) {
                    continue;
                }
                logger.info("Scheduled Time: " + runTime);
                logger.info("Current Time: " + LocalDateTime.now());

                Date scheduleDate = Date.from(
                        runTime.atZone(ZoneId.systemDefault()).toInstant());


            taskScheduler.schedule(
                    () -> executeOfferJob(productId),
                    scheduleDate
            );}
            //  REMOVE OFFER
            LocalDateTime startTime = LocalDateTime.of(date, time);
            LocalDateTime endTime = startTime.plusDays(days);

            Date endDate = Date.from(
                    endTime.atZone(ZoneId.systemDefault()).toInstant()
            );

            taskScheduler.schedule(
                    () -> removeOfferJob(productId),
                    endDate
            );
        }

        // EXECUTION METHOD
        private void executeOfferJob(Long productId) {

            //  Fetch product
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            //  Update offer
            product.setOffer(" 50% OFF LIMITED TIME");
            productRepository.saveAndFlush(product);

            // Fetch users
            List<UserDetails> users = userRepository.findAll();

            //  Send emails
            for (UserDetails user : users) {
                emailService.sendEmail(
                        user.getEmail(),
                        "Special Offer on " + product.getName(),
                        "Hi " + user.getName() +
                                ",\n\nOffer: " + product.getOffer()
                );
            }

            logger.info("Offer applied and emails sent!");
        }
        // remove method
    private void removeOfferJob(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setOffer(null);
        productRepository.saveAndFlush(product);

        logger.info("Offer removed after end date!");
    }

}

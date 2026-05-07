package com.example.ecommerce.controller;

import com.example.ecommerce.service.OfferSchedulerService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OfferController {

    private final OfferSchedulerService offerSchedulerService;

    public OfferController(OfferSchedulerService offerScheduler) {
        this.offerSchedulerService = offerScheduler;
    }

    @PostMapping("/offer/{productId}/{seconds}")
    public String scheduleOffer(@PathVariable Long productId,
                                @PathVariable int seconds) {

        //offerSchedulerService.scheduleOffer(productId, seconds);

        return "Offer scheduled successfully!";
    }
    @PostMapping("/schedule")
    public String scheduleOffer(
            @RequestParam Long productId,
            @RequestParam String date,
            @RequestParam String time,
            @RequestParam int days
    ) {

        offerSchedulerService.scheduleOffer(productId, date, time, days);

        return "Offer scheduled successfully!";
    }
}

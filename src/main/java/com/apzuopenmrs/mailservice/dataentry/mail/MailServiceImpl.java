package com.apzuopenmrs.mailservice.dataentry.mail;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MailServiceImpl {
   // @Scheduled(fixedDelay = 10000)
    public void scheduleFixedDelayTask() {
        System.out.println(
                "Fixed delay task - " + System.currentTimeMillis() / 1000);
    }
}

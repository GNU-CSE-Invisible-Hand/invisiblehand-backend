package com.rrkim;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.rrkim.module.notification.service.NotificationBatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootMainApplicationTests {

    @Autowired
    private NotificationBatchService notificationBatchService;

    @Test
    void contextLoads() throws FirebaseMessagingException, JsonProcessingException {
        notificationBatchService.sendHotIssueNotifications();
        //notificationBatchService.sendInterestIssueNotifications();
    }

}

package com.rrkim.module.notification.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.rrkim.core.common.kafka.KafkaTopic;
import com.rrkim.module.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class KafkaNotificationConsumer {

    private final NotificationService notificationService;


    @Transactional
    //@KafkaListener(topics = KafkaTopic.USER_NOTIFICATIONS, groupId = "invisible_hand")
    public void consumeNotification(Long notificationHistoryIdx) throws JsonProcessingException {
        notificationService.sendNotification(notificationHistoryIdx);
    }


}

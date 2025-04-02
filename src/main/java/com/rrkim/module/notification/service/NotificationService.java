package com.rrkim.module.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.firebase.messaging.*;
import com.rrkim.core.auth.domain.User;
import com.rrkim.core.auth.repository.UserRepository;
import com.rrkim.core.common.exception.UnhandledExecutionException;
import com.rrkim.core.common.kafka.KafkaProducer;
import com.rrkim.core.common.kafka.KafkaTopic;
import com.rrkim.core.common.util.ObjectUtility;
import com.rrkim.module.notification.constant.NotificationStatus;
import com.rrkim.module.notification.constant.NotificationTopic;
import com.rrkim.module.notification.domain.NotificationHistory;
import com.rrkim.module.notification.dto.data.NotificationHistoryDto;
import com.rrkim.module.notification.dto.data.NotificationRequestResultDto;
import com.rrkim.module.notification.dto.request.UpdateNotificationTokenRequestDto;
import com.rrkim.module.notification.repository.NotificationHistoryQRepository;
import com.rrkim.module.notification.repository.NotificationHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.rrkim.module.notification.dto.request.SendNotificationRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class NotificationService {

    private final UserRepository userRepository;
    private final NotificationHistoryRepository notificationHistoryRepository;
    private final KafkaProducer kafkaProducer;
    private final NotificationHistoryQRepository notificationHistoryQRepository;

    @Transactional
    public NotificationRequestResultDto addNotificationQueue(SendNotificationRequestDto sendNotificationRequestDto) throws FirebaseMessagingException, JsonProcessingException {
        String topic = sendNotificationRequestDto.getTopic();
        if(topic == null || topic.isEmpty()) {
            throw new UnhandledExecutionException("notification.invalidTopic");
        }

        Map<String, String> data = sendNotificationRequestDto.getData();
        String dataJson = null;

        if(data != null && !data.isEmpty()) {
            dataJson = ObjectUtility.convertJsonStringFromObject(data);
        }

        NotificationHistory notificationHistory = NotificationHistory.builder()
                .topic(topic)
                .title(sendNotificationRequestDto.getTitle())
                .content(sendNotificationRequestDto.getContent())
                .dataJson(dataJson)
                .notificationStatus(NotificationStatus.REQUEST).build();

        notificationHistoryRepository.save(notificationHistory);
        kafkaProducer.produce(KafkaTopic.USER_NOTIFICATIONS, notificationHistory.getId());
        return ObjectUtility.convertObject(notificationHistory, NotificationRequestResultDto.class);
    }

    @Transactional
    public void sendNotification(Long notificationHistoryIdx) throws JsonProcessingException {
        NotificationHistory notificationHistory = notificationHistoryRepository.findNotificationHistoryById(notificationHistoryIdx)
                .orElseThrow(() -> new UnhandledExecutionException("notification.notificationNotFound"));

        String topic = notificationHistory.getTopic();
        if(topic == null || topic.isEmpty()) {
            throw new UnhandledExecutionException("notification.invalidTopic");
        }

        String dataJson = notificationHistory.getDataJson();
        Map<String, String> data = new HashMap<>();
        if(dataJson != null && !dataJson.isEmpty()) {
            data = ObjectUtility.convertJsonStringToObject(dataJson, new TypeReference<>() {});
        }
        System.out.println("data = " + data);

        Message message = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle(notificationHistory.getTitle())
                        .setBody(notificationHistory.getContent())
                        .build())
                .setTopic(topic)
                .putAllData(data)
                .build();

        try {
            String messageId = FirebaseMessaging.getInstance().send(message);
            notificationHistory.setMessageId(messageId);
            notificationHistory.setNotificationStatus(NotificationStatus.SUCCESS);
        } catch (Exception e) {
            notificationHistory.setNotificationStatus(NotificationStatus.FAIL);
        }
    }

    public List<NotificationHistoryDto> getNotificationHistory(String topic) {
        return notificationHistoryQRepository.getNotificationHistory(topic);
    }
}
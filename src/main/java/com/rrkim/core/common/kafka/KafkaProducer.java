package com.rrkim.core.common.kafka;

import com.rrkim.module.notification.dto.request.SendNotificationRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, Long> kafkaTemplate;

    public void produce(String kafkaTopic, Long id) {
        kafkaTemplate.send(kafkaTopic, id);
    }
}


package com.rrkim.module.notification.constant;

import lombok.Getter;

@Getter
public enum NotificationTopic {

    HOT_ISSUE("hot_issue");

    private final String topic;

    NotificationTopic(String topic) {
        this.topic = topic;
    }

}

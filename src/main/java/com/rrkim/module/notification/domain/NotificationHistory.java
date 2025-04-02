package com.rrkim.module.notification.domain;

import com.rrkim.core.auth.domain.User;
import com.rrkim.core.common.domain.BaseEntity;
import com.rrkim.module.news.domain.Journal;
import com.rrkim.module.news.domain.Tag;
import com.rrkim.module.notification.constant.NotificationStatus;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationHistory extends BaseEntity {

    @Column(name = "topic", nullable = false, length = 30)
    private String topic;

    @Column(name = "title", nullable = false, length = 30)
    private String title;

    @Column(name = "content", nullable = false, length = 100)
    private String content;

    @Column(name = "data_json", length = 100)
    private String dataJson;

    @Setter
    @Column(name = "message_id", length = 100)
    private String messageId;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "notification_status", nullable = false, length = 100)
    private NotificationStatus notificationStatus;
}
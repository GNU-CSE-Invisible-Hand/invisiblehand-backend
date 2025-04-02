package com.rrkim.module.notification.dto.data;

import com.rrkim.module.notification.constant.NotificationStatus;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NotificationRequestResultDto {

    private String topic;

    private String title;

    private String content;

    private NotificationStatus notificationStatus;

}

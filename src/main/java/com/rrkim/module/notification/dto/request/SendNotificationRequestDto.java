package com.rrkim.module.notification.dto.request;

import com.google.firebase.database.annotations.NotNull;
import com.rrkim.module.notification.constant.NotificationTopic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SendNotificationRequestDto {

    private String topic;

    @NotNull
    private String title;

    @NotNull
    private String content;

    private Map<String, String> data;
}

package com.rrkim.module.notification.dto.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.rrkim.core.common.util.ObjectUtility;
import lombok.*;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NotificationHistoryDto {

    private Long notificationHistoryIdx;

    private String topic;

    private String title;

    private String content;

    private Map<String, Object> data;

    public void setDataJson(String dataJson) throws JsonProcessingException {
        this.data = ObjectUtility.convertJsonStringToObject(dataJson, new TypeReference<Map<String, Object>>() {});
    }

}

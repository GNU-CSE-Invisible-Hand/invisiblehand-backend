package com.rrkim.module.notification.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.rrkim.core.common.dto.ApiResponse;
import com.rrkim.core.common.util.ApiUtility;
import com.rrkim.module.notification.dto.data.NotificationHistoryDto;
import com.rrkim.module.notification.dto.data.NotificationRequestResultDto;
import com.rrkim.module.notification.dto.request.UpdateNotificationTokenRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import com.rrkim.module.notification.dto.request.SendNotificationRequestDto;
import com.rrkim.module.notification.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "알림 API")
@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @Operation(summary = "알림 전송", description = "회원에게 알림을 전송합니다")
    @PostMapping("/notification")
    public ApiResponse sendPushNotification(@RequestBody SendNotificationRequestDto sendNotificationRequestDto) throws FirebaseMessagingException, JsonProcessingException {
        NotificationRequestResultDto notificationRequestResultDto = notificationService.addNotificationQueue(sendNotificationRequestDto);
        return ApiUtility.createResponse(true, notificationRequestResultDto);
    }

    @Operation(summary = "알림 기록 조회", description = "회원에게 전송된 알림을 조회합니다.")
    @GetMapping("/notification")
    public ApiResponse getNotificationHistory(@RequestParam @Valid @NotNull @NotEmpty String topic) {
        List<NotificationHistoryDto> notificationHistoryDto = notificationService.getNotificationHistory(topic);
        return ApiUtility.createResponse(true, notificationHistoryDto);
    }
}

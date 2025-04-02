package com.rrkim.module.notification.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateNotificationTokenRequestDto {

    @NotNull
    @NotEmpty
    String token;

    @NotNull
    @NotEmpty
    String userId;
}

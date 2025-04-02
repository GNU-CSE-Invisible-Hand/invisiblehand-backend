package com.rrkim.core.policy.dto.request;

import lombok.*;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Data
public class SetPolicyRequestDto {

    @NotNull
    @NotEmpty
    String policyId;

    String policyValue;
}

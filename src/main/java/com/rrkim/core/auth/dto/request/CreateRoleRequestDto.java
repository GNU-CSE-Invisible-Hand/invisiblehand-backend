package com.rrkim.core.auth.dto.request;

import com.rrkim.core.auth.constant.RoleType;
import lombok.*;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateRoleRequestDto {

    @NotNull
    @NotEmpty
    private RoleType roleId;

    @NotNull
    @NotEmpty
    private String roleNm;
}

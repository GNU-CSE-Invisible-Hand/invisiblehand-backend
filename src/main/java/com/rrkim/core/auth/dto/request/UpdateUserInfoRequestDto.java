package com.rrkim.core.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rrkim.core.auth.constant.Gender;
import com.rrkim.core.auth.domain.UserRole;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateUserInfoRequestDto {

    @NotNull
    @NotEmpty
    private String userId;

    private String originPassword;

    private String newPassword;

    private String userNm;

    private Gender gender;

    private LocalDate birthDate;

    @Builder.Default
    private List<String> interestTagIds = new ArrayList<>();

}

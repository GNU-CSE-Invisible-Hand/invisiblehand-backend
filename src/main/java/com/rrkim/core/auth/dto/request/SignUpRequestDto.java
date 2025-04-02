package com.rrkim.core.auth.dto.request;

import com.rrkim.core.auth.constant.Gender;
import com.rrkim.core.auth.constant.RoleType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SignUpRequestDto {

    @NotNull
    @NotEmpty
    @Email(message = "{auth.invalidEmailFormat}")
    String userId;

    @NotNull
    @NotEmpty
    String password;

    @NotNull
    @NotEmpty
    String userNm;

    @NotNull
    Gender gender;

    @NotNull
    LocalDate birthDate;

    @NotNull
    @Builder.Default
    List<String> interestTagIds = new ArrayList<>();

    @Builder.Default
    List<String> agreedEulaIds = new ArrayList<>();
}

package com.rrkim.core.auth.dto.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.api.client.json.JsonString;
import com.rrkim.core.auth.constant.Gender;
import com.rrkim.core.auth.constant.RoleType;
import com.rrkim.core.auth.domain.Role;
import com.rrkim.core.auth.domain.User;
import com.rrkim.core.auth.domain.UserRole;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    public UserDto(User user) {
        this.userId = user.getUserId();
        this.password = user.getPassword();
        this.userNm = user.getUserNm();
        this.useYn = user.isUseYn();
        this.userRoles = Optional.ofNullable(user.getUserRoles())
                .stream()
                .flatMap(List::stream)
                .map(UserRole::getRole)
                .map(Role::getRoleType)
                .toList();
    }

    private Long userIdx;

    private String userId;

    @JsonIgnore
    private String password;

    private String userNm;

    private Gender gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private boolean useYn;

    @Builder.Default
    private List<String> userInterestTags = new ArrayList<>();

    @JsonIgnore
    @Builder.Default
    private List<RoleType> userRoles = new ArrayList<>();
}

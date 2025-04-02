package com.rrkim.core.auth.dto.data;

import com.rrkim.core.auth.constant.RoleType;
import com.rrkim.core.auth.domain.Role;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleDto {

    public RoleDto(Role role) {
        this.roleType = role.getRoleType();
        this.roleNm = role.getRoleNm();
        this.useYn = role.isUseYn();
    }

    private RoleType roleType;

    private String roleNm;

    private boolean useYn;
}

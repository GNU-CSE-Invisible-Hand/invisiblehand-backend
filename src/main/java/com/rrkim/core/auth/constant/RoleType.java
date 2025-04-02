package com.rrkim.core.auth.constant;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum RoleType {
    ADMIN("ROLE_ADMIN", "관리자"),
    USER("ROLE_USER", "일반회원");

    private final String roleId;
    private final String roleNm;

    RoleType(String roleId, String roleNm) {
        this.roleId = roleId;
        this.roleNm = roleNm;
    }

    public static RoleType fromRoleId(String roleId) {
        return Arrays.stream(values())
                .filter(role -> role.getRoleId().equals(roleId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown roleId: " + roleId));
    }
}

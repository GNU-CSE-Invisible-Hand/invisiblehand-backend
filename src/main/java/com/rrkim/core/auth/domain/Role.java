package com.rrkim.core.auth.domain;

import com.rrkim.core.auth.constant.RoleType;
import com.rrkim.core.common.domain.BaseEntity;
import lombok.*;

import jakarta.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "role_id", unique = true)
    private RoleType roleType;

    @Column(name = "role_nm")
    private String roleNm;

    @Column(name = "use_yn", nullable = false, columnDefinition = BaseEntity.USE_YN_ENUM)
    private boolean useYn;
}

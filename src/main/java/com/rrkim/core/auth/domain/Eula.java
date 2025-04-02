package com.rrkim.core.auth.domain;

import com.rrkim.core.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table
public class Eula extends BaseEntity {

    @Column(name = "eula_id", nullable = false, length = 50, unique = true)
    private String eulaId;

    @Column(name = "content", nullable = false, columnDefinition = BaseEntity.TEXT)
    private String content;

    @Column(name = "require_yn", nullable = false)
    private boolean requireYn;
}

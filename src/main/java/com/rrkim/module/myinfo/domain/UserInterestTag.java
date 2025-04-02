package com.rrkim.module.myinfo.domain;

import com.rrkim.core.auth.domain.User;
import com.rrkim.core.common.domain.BaseEntity;
import com.rrkim.module.news.domain.Tag;
import lombok.*;

import jakarta.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table
public class UserInterestTag extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_idx", nullable = false)
    private Tag tag;

}
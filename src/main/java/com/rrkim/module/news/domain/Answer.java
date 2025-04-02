package com.rrkim.module.news.domain;

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
public class Answer extends BaseEntity {

    @Column(name = "answer_content", nullable = false, length = 100)
    private String answerContent;

    @Column(name = "answer_yn", nullable = false, columnDefinition = BaseEntity.USE_YN_ENUM)
    private boolean answerYn;

}

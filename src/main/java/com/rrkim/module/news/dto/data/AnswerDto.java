package com.rrkim.module.news.dto.data;

import com.rrkim.core.common.domain.BaseEntity;
import com.rrkim.module.news.domain.Answer;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AnswerDto {

    public AnswerDto(Answer answer) {
        this.answerIdx = answer.getId();
        this.answerContent = answer.getAnswerContent();
        this.answerYn = answer.isAnswerYn();
    }

    private Long answerIdx;

    private String answerContent;

    private boolean answerYn;

}

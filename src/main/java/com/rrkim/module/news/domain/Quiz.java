package com.rrkim.module.news.domain;

import com.rrkim.core.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table
public class Quiz extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_idx", nullable = false)
    private Article article;

    @Column(name = "quiz_content", nullable = false, length = 100)
    private String quizContent;

    @Builder.Default
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<QuizAnswer> quizAnswers = new ArrayList<>();

    public void addAnswers(List<Answer> answers) {
        for (Answer answer : answers) {
            QuizAnswer quizAnswer = QuizAnswer.builder().quiz(this).answer(answer).build();
            this.quizAnswers.add(quizAnswer);
        }
    }
}


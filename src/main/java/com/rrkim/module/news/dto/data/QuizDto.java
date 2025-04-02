package com.rrkim.module.news.dto.data;

import com.rrkim.core.common.util.ObjectUtility;
import com.rrkim.module.news.domain.Article;
import com.rrkim.module.news.domain.Journal;
import com.rrkim.module.news.domain.Quiz;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class QuizDto {

    public QuizDto(Quiz quiz) {
        this.quizIdx = quiz.getId();
        this.quizContent = quiz.getQuizContent();

        if (Hibernate.isInitialized(quiz.getArticle())) {
            this.articleIdx = ObjectUtility.getObject(quiz.getArticle(), Article::getId);
        }
        if (Hibernate.isInitialized(quiz.getQuizAnswers())) {
            this.answers = ObjectUtility.getObjects(quiz.getQuizAnswers(), d -> new AnswerDto(d.getAnswer()));
        }
    }

    private Long quizIdx;

    private Long articleIdx;

    private String quizContent;

    @Builder.Default
    private List<AnswerDto> answers = new ArrayList<>();

}

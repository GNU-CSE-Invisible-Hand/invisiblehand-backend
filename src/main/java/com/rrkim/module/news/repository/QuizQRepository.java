package com.rrkim.module.news.repository;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rrkim.module.news.dto.data.AnswerDto;
import com.rrkim.module.news.dto.data.ArticleDto;
import com.rrkim.module.news.dto.data.NewsDto;
import com.rrkim.module.news.dto.data.QuizDto;
import com.rrkim.module.news.dto.request.SelectArticleQuizListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.rrkim.module.news.domain.QAnswer.answer;
import static com.rrkim.module.news.domain.QArticle.article;
import static com.rrkim.module.news.domain.QJournal.journal;
import static com.rrkim.module.news.domain.QNews.news;
import static com.rrkim.module.news.domain.QNewsArticle.newsArticle;
import static com.rrkim.module.news.domain.QQuiz.quiz;
import static com.rrkim.module.news.domain.QQuizAnswer.quizAnswer;

@RequiredArgsConstructor
@Repository
public class QuizQRepository {

    private final JPAQueryFactory queryFactory;

    public List<QuizDto> selectQuizList(SelectArticleQuizListDto selectArticleQuizListDto) {
        return queryFactory
                .from(quiz)
                .leftJoin(quiz.article, article)
                .leftJoin(quiz.quizAnswers, quizAnswer)
                .leftJoin(quizAnswer.answer, answer)
                .where(
                        eqArticleIdx(selectArticleQuizListDto.getArticleIdx())
                ).transform(GroupBy.groupBy(quiz.id).list(
                        Projections.bean(QuizDto.class,
                                quiz.id.as("quizIdx"),
                                quiz.article.id.as("articleIdx"),
                                quiz.quizContent,
                                GroupBy.list(Projections.bean(AnswerDto.class,
                                        answer.id.as("answerIdx"),
                                        answer.answerContent,
                                        answer.answerYn
                                )).as("answers")
                        )
                ));
    }

    private BooleanExpression eqArticleIdx(Long articleIdx) {
        if(articleIdx == null) { return null; }
        return article.id.eq(articleIdx);
    }
}

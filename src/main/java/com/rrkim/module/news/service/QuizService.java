package com.rrkim.module.news.service;

import com.rrkim.core.common.exception.UnhandledExecutionException;
import com.rrkim.module.news.domain.Answer;
import com.rrkim.module.news.domain.Article;
import com.rrkim.module.news.domain.Quiz;
import com.rrkim.module.news.dto.request.CreateAnswerRequestDto;
import com.rrkim.module.news.dto.request.CreateQuizRequestDto;
import com.rrkim.module.news.dto.data.QuizDto;
import com.rrkim.module.news.dto.request.SelectArticleQuizListDto;
import com.rrkim.module.news.repository.ArticleRepository;
import com.rrkim.module.news.repository.QuizQRepository;
import com.rrkim.module.news.repository.QuizRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class QuizService {

    private final ArticleRepository articleRepository;
    private final QuizQRepository quizQRepository;
    private final QuizRepository quizRepository;

    public List<QuizDto> selectQuizList(SelectArticleQuizListDto selectArticleQuizListDto) {
        return quizQRepository.selectQuizList(selectArticleQuizListDto);
    }

    public QuizDto selectArticleQuiz(@Valid Long articleIdx) {
        List<QuizDto> quizDtos = quizQRepository.selectQuizList(SelectArticleQuizListDto.builder().articleIdx(articleIdx).build());
        if(quizDtos == null || quizDtos.isEmpty()) {
            throw new UnhandledExecutionException("quiz.quizNotFound");
        }

        Collections.shuffle(quizDtos);
        return quizDtos.get(0);
    }

    @Transactional
    public QuizDto createQuiz(@Valid CreateQuizRequestDto createQuizRequestDto) {
        List<CreateAnswerRequestDto> createAnswerRequestDtos = createQuizRequestDto.getAnswers();
        List<Answer> answers = createAnswerRequestDtos.stream().map(d -> Answer.builder()
                .answerContent(d.getAnswerContent())
                .answerYn(d.isAnswerYn()).build()
        ).toList();

        if(answers.stream().noneMatch(Answer::isAnswerYn)) {
            throw new UnhandledExecutionException("quiz.answerYnTrueNotExists");
        }

        Article article = articleRepository.findArticleById(createQuizRequestDto.getArticleIdx());
        if(article == null) {
            throw new UnhandledExecutionException("article.articleNotFound");
        }

        Quiz quiz = Quiz.builder()
                .article(article)
                .quizContent(createQuizRequestDto.getQuizContent()).build();
        quiz.addAnswers(answers);

        quizRepository.save(quiz);
        return new QuizDto(quiz);
    }


}

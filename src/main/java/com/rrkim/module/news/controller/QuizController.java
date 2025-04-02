package com.rrkim.module.news.controller;

import com.rrkim.core.common.dto.ApiResponse;
import com.rrkim.core.common.util.ApiUtility;
import com.rrkim.module.news.dto.request.CreateQuizRequestDto;
import com.rrkim.module.news.dto.data.QuizDto;
import com.rrkim.module.news.dto.request.SelectArticleQuizListDto;
import com.rrkim.module.news.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "퀴즈 API")
public class QuizController {

    private final QuizService quizService;

    @Operation(summary = "퀴즈 조회", description = "퀴즈를 조회합니다")
    @GetMapping("/quiz")
    public ApiResponse selectQuizList(SelectArticleQuizListDto selectArticleQuizListDto) {
        List<QuizDto> quizDtos = quizService.selectQuizList(selectArticleQuizListDto);
        return ApiUtility.createResponse(true, quizDtos);
    }

    @PostMapping("/quiz")
    @Operation(summary = "퀴즈 등록", description = "새로운 퀴즈를 등록합니다")
    public ApiResponse createQuiz(@RequestBody @Valid CreateQuizRequestDto createQuizRequestDto) {
        QuizDto quizDto = quizService.createQuiz(createQuizRequestDto);
        return ApiUtility.createResponse(true, quizDto);
    }

    @GetMapping("/quiz/{articleIdx}")
    @Operation(summary = "아티클에 대한 퀴즈 단건 랜덤 조회", description = "아티클에 대한 퀴즈를 랜덤하여 단건 조회합니다")
    public ApiResponse selectArticleQuiz(@Valid @PathVariable Long articleIdx) {
        QuizDto quizDto = quizService.selectArticleQuiz(articleIdx);
        return ApiUtility.createResponse(true, quizDto);
    }
}

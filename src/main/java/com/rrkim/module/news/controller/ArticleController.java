package com.rrkim.module.news.controller;

import com.rrkim.core.common.dto.ApiResponse;
import com.rrkim.core.common.exception.UnhandledExecutionException;
import com.rrkim.core.common.util.ApiUtility;
import com.rrkim.core.common.util.HashMapCreator;
import com.rrkim.module.news.constant.ReactionType;
import com.rrkim.module.news.dto.data.ArticleDto;
import com.rrkim.module.news.dto.data.JournalDto;
import com.rrkim.module.news.dto.data.NewsDto;
import com.rrkim.module.news.dto.request.CreateArticleRequestDto;
import com.rrkim.module.news.dto.request.SelectArticleRequestDto;
import com.rrkim.module.news.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "아티클 API")
public class ArticleController {

    private final ArticleService articleService;

    @Operation(summary = "아티클 조회", description = "아티클을 조회합니다")
    @GetMapping("/article")
    public ApiResponse selectArticleList(@Valid SelectArticleRequestDto selectArticleRequestDto) {
        List<ArticleDto> articleList = articleService.selectArticleList(selectArticleRequestDto);
        return ApiUtility.createResponse(true, articleList, articleList.size());
    }

    @Operation(summary = "아티클 조회", description = "아티클을 조회합니다")
    @GetMapping("/article/{articleIdx}")
    public ApiResponse selectArticleDetail(@Valid @PathVariable Long articleIdx) {
        ArticleDto articleDto = articleService.selectArticleDetail(articleIdx);
        return ApiUtility.createResponse(true, articleDto);
    }

    @Operation(summary = "추천 아티클 조회", description = "추천 아티클을 조회합니다")
    @GetMapping("/article/recommend")
    public ApiResponse getRecommendArticles(@RequestParam String userId,
                                            @PageableDefault(size = 10) Pageable pageable) {
        List<ArticleDto> recommendArticles = articleService.getRecommendedArticles(userId, pageable);
        return ApiUtility.createResponse(true, recommendArticles, recommendArticles.size());
    }

    @Operation(summary = "아티클 등록", description = "새로운 아티클을 등록합니다")
    @PostMapping("/article")
    public ApiResponse createArticle(@RequestBody @Valid CreateArticleRequestDto createArticleRequestDto) {
        ArticleDto articleDto = articleService.createArticle(createArticleRequestDto);
        return ApiUtility.createResponse(true, articleDto);
    }

    @Operation(summary = "아티클 리액션 기능", description = "사용자가 아티클에 대한 리액션을 추가하거나 취소합니다")
    @PostMapping("/article/{articleIdx}/reaction")
    public ApiResponse updateArticleReaction(@RequestParam String userId,
                                             @PathVariable Long articleIdx,
                                             @RequestParam String reaction){
        // ReactionType으로 변환
        ReactionType reactionType;
        try {
            reactionType = ReactionType.valueOf(reaction.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnhandledExecutionException("유효하지 않은 리액션 타입입니다.");
        }

        articleService.toggleReaction(userId, articleIdx, reactionType);
        return ApiUtility.createResponse(true, "리액션 업데이트 성공");
    }
}

package com.rrkim.module.news.controller;

import com.rrkim.core.common.dto.ApiResponse;
import com.rrkim.core.common.util.ApiUtility;
import com.rrkim.module.news.dto.data.ArticleDto;
import com.rrkim.module.news.dto.data.NewsDto;
import com.rrkim.module.news.dto.request.CreateArticleRequestDto;
import com.rrkim.module.news.dto.request.CreateNewsRequestDto;
import com.rrkim.module.news.dto.request.DeleteNewsRequestDto;
import com.rrkim.module.news.dto.request.SelectNewsRequestDto;
import com.rrkim.module.news.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Tag(name = "뉴스 API")
@RestController
public class NewsController {

    private final NewsService newsService;

    @Operation(summary = "뉴스 조회", description = "뉴스를 조회합니다")
    @GetMapping("/news")
    public ApiResponse selectNewsList(@Valid SelectNewsRequestDto selectNewsRequestDto) {
        List<NewsDto> newsList = newsService.selectNewsList(selectNewsRequestDto);
        return ApiUtility.createResponse(true, newsList, newsList.size());
    }

    @Operation(summary = "뉴스 등록", description = "새로운 뉴스를 등록합니다")
    @PostMapping("/news")
    public ApiResponse createNews(@RequestBody @Valid CreateNewsRequestDto createNewsRequestDto) throws IOException {
        NewsDto newsDto = newsService.createNews(createNewsRequestDto);
        return ApiUtility.createResponse(true, newsDto);
    }

    @Operation(summary = "뉴스 삭제", description = "뉴스를 삭제합니다")
    @DeleteMapping("/news")
    public ApiResponse deleteNews(@RequestBody @Valid DeleteNewsRequestDto deleteNewsRequestDto) {
        List<NewsDto> newsDtoList = newsService.deleteNews(deleteNewsRequestDto);
        return ApiUtility.createResponse(true, newsDtoList, newsDtoList.size());
    }
}

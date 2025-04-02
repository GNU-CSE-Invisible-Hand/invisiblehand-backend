package com.rrkim.module.news.controller;

import com.rrkim.core.common.dto.ApiResponse;
import com.rrkim.core.common.util.ApiUtility;
import com.rrkim.module.news.dto.data.ArticleDto;
import com.rrkim.module.news.dto.data.KeywordDto;
import com.rrkim.module.news.dto.request.*;
import com.rrkim.module.news.service.ArticleService;
import com.rrkim.module.news.service.KeywordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "키워드 API")
public class KeywordController {

    private final KeywordService keywordService;

    @Operation(summary = "키워드 조회", description = "키워드를 조회합니다")
    @GetMapping("/keyword")
    public ApiResponse selectKeywordList(SelectKeywordRequestDto selectKeywordRequestDto) {
        List<KeywordDto> keywordList = keywordService.selectKeywordList(selectKeywordRequestDto);
        return ApiUtility.createResponse(true, keywordList, keywordList.size());
    }

    @Operation(summary = "키워드 등록", description = "키워드를 등록합니다.")
    @PostMapping("/keyword")
    public ApiResponse createKeyword(CreateKeywordRequestDto createKeywordRequestDto) {
        KeywordDto keywordDto = keywordService.createKeyword(createKeywordRequestDto);
        return ApiUtility.createResponse(true, keywordDto);
    }

    @Operation(summary = "키워드 연결", description = "아티클에 키워드를 연결합니다")
    @GetMapping("/keyword/mapping-article")
    public ApiResponse mappingArticle(MappingArticleKeywordRequestDto mappingArticleKeywordRequestDto) {
        KeywordDto keywordDto = keywordService.mappingKeyword(mappingArticleKeywordRequestDto);
        return ApiUtility.createResponse(true, keywordDto);
    }

    @Operation(summary = "키워드 연결취소", description = "아티클에 키워드를 연결 해제합니다")
    @GetMapping("/keyword/unmapping-article")
    public ApiResponse unmappingArticle(MappingArticleKeywordRequestDto mappingArticleKeywordRequestDto) {
        KeywordDto keywordDto = keywordService.unmappingKeyword(mappingArticleKeywordRequestDto);
        return ApiUtility.createResponse(true, keywordDto);
    }
}

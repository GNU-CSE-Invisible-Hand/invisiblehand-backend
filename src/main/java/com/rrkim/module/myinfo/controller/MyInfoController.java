package com.rrkim.module.myinfo.controller;

import com.rrkim.core.common.dto.ApiResponse;
import com.rrkim.core.common.util.ApiUtility;
import com.rrkim.core.common.util.HashMapCreator;
import com.rrkim.module.myinfo.dto.UserInfoResponseDto;
import com.rrkim.module.myinfo.service.MyInfoService;
import com.rrkim.module.news.domain.Article;
import com.rrkim.module.news.dto.data.ArticleDto;
import com.rrkim.module.news.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

@Tag(name = "내 정보 API")
@RequiredArgsConstructor
@RestController
public class MyInfoController {

    private final MyInfoService userInfoService;
    private final ArticleService articleService;

    // 사용자 정보 조회 (읽은 아티클, 좋아요한 뉴스, 관심 등록한 태그 및 언론사 관련 뉴스)
    @Operation(summary = "내 정보 조회", description = "내 정보를 조회합니다")
    @GetMapping("/user/info")
    public ApiResponse getUserInfo(@RequestParam Long userId, @RequestParam(required = false, defaultValue = "month") String period) {

        UserInfoResponseDto userInfo = userInfoService.getUserInfo(userId, period);

        if (userInfo.getReadArticles().isEmpty() && userInfo.getLikedNews().isEmpty() && userInfo.getInterestedTags().isEmpty()) {
            return ApiUtility.createResponse(true, null);
        }
        return ApiUtility.createResponse(true, userInfo);
    }

    // 사용자 정보 조회 (타입별 조회)
    @GetMapping("/user/info/type")
    public ApiResponse getUserInfoByType(
            @RequestParam Long userId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false, defaultValue = "month") String period) {

        UserInfoResponseDto userInfo = userInfoService.getUserInfo(userId, period);

        // 조회한 카테고리를 제외한 나머지 카테고리는 빈 리스트
        if (type != null) {
            switch (type.toLowerCase()) {
                case "read":
                    userInfo.setLikedNews(Collections.emptyList());
                    userInfo.setInterestedTags(Collections.emptyList());
                    break;
                case "liked":
                    userInfo.setReadArticles(Collections.emptyList());
                    userInfo.setInterestedTags(Collections.emptyList());
                    break;
                case "interested":
                    userInfo.setReadArticles(Collections.emptyList());
                    userInfo.setLikedNews(Collections.emptyList());
                    break;
                default:
                    return ApiUtility.createResponse(true, null);
            }
        }

        if (userInfo.getReadArticles().isEmpty() && userInfo.getLikedNews().isEmpty()
                && userInfo.getInterestedTags().isEmpty()) {
            return ApiUtility.createResponse(true, null);
        }

        return ApiUtility.createResponse(true, userInfo);
    }
}
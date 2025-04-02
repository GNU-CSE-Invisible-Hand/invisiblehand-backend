package com.rrkim.module.myinfo.service;

import com.rrkim.module.myinfo.domain.UserInterestTag;
import com.rrkim.module.myinfo.domain.UserLikedNews;
import com.rrkim.module.myinfo.domain.UserReadArticle;
import com.rrkim.module.myinfo.dto.UserInfoResponseDto;
import com.rrkim.module.myinfo.repository.UserInterestTagRepository;
import com.rrkim.module.myinfo.repository.UserLikedNewsRepository;
import com.rrkim.module.myinfo.repository.UserReadArticleRepository;
import com.rrkim.module.news.domain.Article;
import com.rrkim.module.news.domain.Journal;
import com.rrkim.module.news.domain.Tag;
import com.rrkim.module.news.dto.data.ArticleDto;
import com.rrkim.module.news.dto.data.JournalDto;
import com.rrkim.module.news.dto.data.TagDto;
import com.rrkim.module.news.repository.ArticleRepository;
import com.rrkim.module.news.repository.JournalRepository;
import com.rrkim.module.news.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyInfoService {

    private final TagRepository tagRepository;
    private final UserInterestTagRepository userInterestTagRepository;
    private final UserReadArticleRepository userReadArticleRepository;
    private final UserLikedNewsRepository userLikedNewsRepository;

    // 사용자 정보 조회 (읽은 아티클, 좋아요한 뉴스, 관심 등록한 태그 및 언론사 관련 뉴스)
    public UserInfoResponseDto getUserInfo(Long userIdx, String period) {

        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate;

        switch (period.toLowerCase()) {
            case "day":
                startDate = endDate.minusDays(1);
                break;
            case "week":
                startDate = endDate.minusWeeks(1);
                break;
            case "month":
                startDate = endDate.minusMonths(1);
                break;
            default:
                throw new IllegalArgumentException("Invalid period specified. Use 'day', 'week', or 'month'.");
        }

        // 읽은 아티클 목록
        List<UserReadArticle> readArticles = userReadArticleRepository.findAllByUser_IdAndReadTimeBetween(userIdx, startDate, endDate);
        List<ArticleDto> readArticleDtos = readArticles.stream()
                .map(readArticle -> new ArticleDto(readArticle.getArticle()))
                .collect(Collectors.toList());

        // 공감한 아티클 목록
        List<UserLikedNews> likedArticles = userLikedNewsRepository.findAllByUser_Id(userIdx);
        List<ArticleDto> likedNewsDtos = likedArticles.stream()
                .limit(10)
                .map(likedArticle -> new ArticleDto(likedArticle.getArticle()))
                .collect(Collectors.toList());

        // 관심 등록한 태그와 언론사 관련 목록
        List<UserInterestTag> userInterestTags = userInterestTagRepository.findByUser_Id(userIdx);

        // 태그 ID 목록 수집 후 일괄 조회
        List<String> tagIds = userInterestTags.stream()
                .filter(interest -> interest.getTag() != null)
                .map(interest -> interest.getTag().getTagId())
                .collect(Collectors.toList());

        List<TagDto> interestedTags = new ArrayList<>();
        if(!tagIds.isEmpty()){
            interestedTags = tagRepository.findTagByTagIdIn(tagIds).stream()
                    .map(TagDto::new)
                    .collect(Collectors.toList());
        }

        return new UserInfoResponseDto(readArticleDtos, likedNewsDtos, interestedTags);
    }
}
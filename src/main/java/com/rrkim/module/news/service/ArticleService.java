package com.rrkim.module.news.service;

import com.rrkim.core.auth.domain.User;
import com.rrkim.core.auth.dto.data.UserDto;
import com.rrkim.core.auth.repository.UserRepository;
import com.rrkim.core.common.exception.UnhandledExecutionException;
import com.rrkim.module.myinfo.domain.UserLikedNews;
import com.rrkim.module.myinfo.domain.UserReadArticle;
import com.rrkim.module.myinfo.repository.UserLikedNewsRepository;
import com.rrkim.module.myinfo.repository.UserReadArticleRepository;
import com.rrkim.module.news.constant.ReactionType;
import com.rrkim.module.news.domain.Article;
import com.rrkim.module.news.domain.News;
import com.rrkim.module.news.dto.data.ArticleDto;
import com.rrkim.module.news.dto.data.NewsDto;
import com.rrkim.module.news.dto.request.SelectArticleRequestDto;
import com.rrkim.module.news.dto.request.SelectNewsRequestDto;
import com.rrkim.module.news.repository.ArticleQRepository;
import com.rrkim.module.news.repository.ArticleRepository;
import com.rrkim.module.news.dto.data.JournalDto;
import com.rrkim.module.news.dto.request.CreateArticleRequestDto;
import com.rrkim.module.news.repository.JournalRepository;
import com.rrkim.module.news.repository.NewsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ArticleService {

    private final JournalRepository journalRepository;
    private final ArticleRepository articleRepository;
    private final ArticleQRepository articleQRepository;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final UserReadArticleRepository userReadArticleRepository;
    private final UserLikedNewsRepository userLikedNewsRepository;
    public List<ArticleDto> selectArticleList(SelectArticleRequestDto selectArticleRequestDto) {
        return articleQRepository.find(selectArticleRequestDto);
    }

    @Transactional
    public ArticleDto selectArticleDetail(@Valid Long articleIdx) {
        SelectArticleRequestDto selectArticleRequestDto = SelectArticleRequestDto.builder()
                .articleIdx(articleIdx)
                .build();

        List<ArticleDto> articleDtos = articleQRepository.find(selectArticleRequestDto);

        if (articleDtos.isEmpty()) {
            throw new UnhandledExecutionException("article.articleNotFound");
        }

        //읽은 아티클 조회수 증가
        Article article = articleRepository.findById(articleIdx)
                .orElseThrow(() -> new UnhandledExecutionException("article.articleNotFound"));
        article.incrementViews();

        // 단일 아티클 반환
        return articleDtos.get(0);

        /*Article article = articleRepository.findById(articleIdx)
                .orElseThrow(() -> new UnhandledExecutionException("article.articleNotFound"));

        // 조회수 증가 옵션
        article.incrementViews();

        return new ArticleDto(article);
        return articleQRepository.find(articleIdx);*/
    }

    //추천 아티클 리스트 반환
    @Transactional(readOnly = true)
    public List<ArticleDto> getRecommendedArticles(String userId, Pageable pageable) {

        return articleQRepository.findRecommendedArticles(userId, pageable);
    }

    @Transactional
    public ArticleDto createArticle(CreateArticleRequestDto createArticleRequestDto) {
        Article article = Article.builder()
                .title(createArticleRequestDto.getTitle())
                .content(createArticleRequestDto.getContent())
                .publicationDate(createArticleRequestDto.getPublicationDate()).build();

        List<Long> newsIdxes = createArticleRequestDto.getNewsIdxes();
        List<News> newses = newsRepository.findNewsByIdIn(newsIdxes);
        if(newses.isEmpty()) {
            throw new UnhandledExecutionException("news.createArticleNewsNotFound");
        }
        article.addNews(newses);

        articleRepository.save(article);
        return new ArticleDto(article);
    }

    public List<ArticleDto> getArticlesReadByUserWithinPeriod(Long userId, String period) {
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

        List<UserReadArticle> readArticles = userReadArticleRepository.findAllByUser_IdAndReadTimeBetween(userId, startDate, endDate);
        return readArticles.stream()
                .map(readArticle -> new ArticleDto(readArticle.getArticle()))
                .collect(Collectors.toList());
    }

    // 사용자 아티클 리액션 기능
    @Transactional
    public void toggleReaction(String userId, Long articleIdx, ReactionType reactionType) {
        User user = userRepository.findByUserId(userId);

        if (user == null) {
            throw new UnhandledExecutionException("auth.userNotFound");
        }

        Article article = articleRepository.findById(articleIdx)
                .orElseThrow(() -> new UnhandledExecutionException("article.articleNotFound"));

        Optional<UserLikedNews> existingReaction = userLikedNewsRepository.findByUserAndArticle(user, article);

        if (existingReaction.isPresent()) {
            UserLikedNews userLikedNews = existingReaction.get();
            if (userLikedNews.getReactionType().equals(reactionType)) {
                // 동일한 리액션을 다시 누를 경우 리액션 취소
                userLikedNewsRepository.delete(userLikedNews);
                article.updateReactionCount(reactionType, false);
            } else {
                // 다른 리액션으로 변경
                ReactionType oldReactionType = userLikedNews.getReactionType();
                userLikedNews.changeReactionType(reactionType);
                article.updateReactionCount(oldReactionType, false);
                article.updateReactionCount(reactionType, true);
            }
        } else {
            // 새로운 리액션 추가
            UserLikedNews newReaction = UserLikedNews.builder()
                    .user(user)
                    .article(article)
                    .reactionType(reactionType)
                    .build();
            userLikedNewsRepository.save(newReaction);
            article.updateReactionCount(reactionType, true);
        }
    }
}

package com.rrkim.module.news.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rrkim.core.common.repository.AbstractRepository;
import com.rrkim.module.news.domain.Article;
import com.rrkim.module.news.dto.data.ArticleDto;
import com.rrkim.module.news.dto.data.NewsDto;
import com.rrkim.module.news.dto.request.SelectArticleRequestDto;
import com.rrkim.module.news.dto.request.SelectNewsRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.rrkim.module.myinfo.domain.QUserInterestTag.userInterestTag;
import static com.rrkim.module.news.domain.QArticle.article;
import static com.rrkim.module.news.domain.QArticleKeyword.articleKeyword;
import static com.rrkim.module.news.domain.QJournal.journal;
import static com.rrkim.module.news.domain.QKeyword.keyword;
import static com.rrkim.module.news.domain.QNews.news;
import static com.rrkim.module.news.domain.QNewsArticle.newsArticle;
import static com.rrkim.module.news.domain.QNewsTag.newsTag;
import static com.rrkim.module.news.domain.QTag.tag;

@RequiredArgsConstructor
@Repository
public class ArticleQRepository extends AbstractRepository<Article> {

    private final JPAQueryFactory queryFactory;
    private static final int minViews = 50;
    private static final int minReactions = 50;
    private static final int maxRecentCount = 50;

    public List<Article> find(List<Long> articleIdxes) {
        return queryFactory
                .select(article)
                .from(article)
                    .leftJoin(article.articleKeywords, articleKeyword).fetchJoin()
                    .leftJoin(articleKeyword.keyword, keyword).fetchJoin()
                .where(
                        article.id.in(articleIdxes)
                ).fetch();
    }

    public List<ArticleDto> find(SelectArticleRequestDto selectArticleRequestDto) {
        return queryFactory
                .from(article)
                    .leftJoin(article.newsArticles, newsArticle)
                    .leftJoin(newsArticle.news, news)
                    .leftJoin(news.journal, journal)
                    .leftJoin(news.newsPhotoFile)
                .where(
                    eqArticleIdx(selectArticleRequestDto.getArticleIdx()),
                    inJournalIds(selectArticleRequestDto.getJournalIds()),
                    inTagIds(selectArticleRequestDto.getTagIds())
                ).transform(GroupBy.groupBy(article.id).list(
                    Projections.bean(ArticleDto.class,
                            article.id.as("articleIdx"),
                            article.title,
                            article.content,
                            article.views,
                            article.likeCount,
                            article.empathyCount,
                            article.dislikeCount,
                            article.amazingCount,
                            article.angryCount,
                            article.publicationDate,
                            GroupBy.list(Projections.bean(NewsDto.class,
                                news.id.as("newsIdx"),
                                news.journal.journalId,
                                news.title,
                                news.link,
                                news.newsPhotoFile.fileId.as("photoFileId"),
                                news.publicationDate
                            )).as("news")
                    )
                ));
    }

    public ArticleDto find(Long articleIdx) {
        SelectArticleRequestDto selectArticleRequestDto = SelectArticleRequestDto.builder().articleIdx(articleIdx).build();
        return find(selectArticleRequestDto).stream().findFirst().orElse(null);
    }

    private BooleanExpression eqArticleIdx(Long articleIdx) {
        if(articleIdx == null) { return null; }
        return article.id.eq(articleIdx);
    }

    private BooleanExpression inJournalIds(List<String> journalIds) {
        if(journalIds == null || journalIds.isEmpty()) { return null; }
        return article.id.in(JPAExpressions
                .select(article.id)
                .from(article)
                    .join(article.newsArticles, newsArticle)
                    .join(newsArticle.news, news)
                    .join(news.journal, journal)
                .where(
                        journal.journalId.in(journalIds)
                )
        );
    }

    private BooleanExpression inTagIds(List<String> tagIds) {
        if(tagIds == null || tagIds.isEmpty()) { return null; }
        return news.id.in(JPAExpressions
                .select(newsTag.news.id)
                .from(newsTag)
                .join(newsTag.tag, tag)
                .where(
                        tag.tagId.in(tagIds)
                )
        );
    }

    private List<Long> getUserInterestTagIds(String userId){
        return queryFactory
                .select(userInterestTag.tag.id)
                .from(userInterestTag)
                .where(userInterestTag.user.userId.eq(userId))
                .fetch();
    }

    //각 아티클 병합을 통한 추천 아티클 리스트 생성
    public List<ArticleDto> findRecommendedArticles(String userId, Pageable pageable) {
        long pageOffset = pageable.getOffset();
        int pageSize = pageable.getPageSize();

        // 최신 아티클 조회
        List<ArticleDto> findRecentArticles = getRecentArticleDtos();

        // 인기 아티클 조회
        List<ArticleDto> findPopularArticles = getPopularArticleDtos(pageOffset, pageSize);

        // 관심 태그 기반 아티클 조회
        List<Long> interestTagIds = getUserInterestTagIds(userId);
        List<ArticleDto> findInterestArticles = getInterestArticleDtos(interestTagIds, pageOffset, pageSize);

        // 병합
        return mergeArticles(findRecentArticles, findPopularArticles, findInterestArticles, pageSize);
    }

    public List<ArticleDto> getInterestArticleDtos(List<Long> interestTagIds, long pageOffset, int pageSize) {
        List<ArticleDto> findInterestArticles = queryFactory
                .from(article)
                .leftJoin(article.newsArticles, newsArticle)
                .leftJoin(newsArticle.news, news)
                .leftJoin(news.newsTags, newsTag)
                .leftJoin(news.journal, journal)
                .leftJoin(news.newsPhotoFile)
                .where(newsTag.tag.id.in(interestTagIds))
                .orderBy(article.publicationDate.desc())
                .offset(pageOffset)
                .limit(pageSize)
                .transform(GroupBy.groupBy(article.id).list(
                        Projections.bean(ArticleDto.class,
                                article.id.as("articleIdx"),
                                article.title,
                                article.content,
                                article.views,
                                article.likeCount,
                                article.empathyCount,
                                article.dislikeCount,
                                article.amazingCount,
                                article.angryCount,
                                article.publicationDate,
                                Expressions.asString("INTEREST").as("recommendType"),
                                GroupBy.list(Projections.bean(NewsDto.class,
                                        news.id.as("newsIdx"),
                                        news.journal.journalId,
                                        news.title,
                                        news.link,
                                        news.newsPhotoFile.fileId.as("photoFileId"),
                                        news.publicationDate
                                )).as("news")
                        )
                ));
        return findInterestArticles;
    }

    public List<ArticleDto> getPopularArticleDtos(long pageOffset, int pageSize) {
        List<ArticleDto> findPopularArticles = queryFactory
                .from(article)
                .leftJoin(article.newsArticles, newsArticle)
                .leftJoin(newsArticle.news, news)
                .leftJoin(news.journal, journal)
                .leftJoin(news.newsPhotoFile)
                .where(
                        article.likeCount.add(article.empathyCount)
                                .add(article.amazingCount)
                                .add(article.dislikeCount)
                                .add(article.angryCount)
                                .goe(minReactions)
                                .or(article.views.goe(minViews))
                )
                .orderBy(article.views.desc())
                .offset(pageOffset)
                .limit(pageSize)
                .transform(GroupBy.groupBy(article.id).list(
                        Projections.bean(ArticleDto.class,
                                article.id.as("articleIdx"),
                                article.title,
                                article.content,
                                article.views,
                                article.likeCount,
                                article.empathyCount,
                                article.dislikeCount,
                                article.amazingCount,
                                article.angryCount,
                                article.publicationDate,
                                Expressions.asString("POPULAR").as("recommendType"),
                                GroupBy.list(Projections.bean(NewsDto.class,
                                        news.id.as("newsIdx"),
                                        news.journal.journalId,
                                        news.title,
                                        news.link,
                                        news.newsPhotoFile.fileId.as("photoFileId"),
                                        news.publicationDate
                                )).as("news")
                        )
                ));
        return findPopularArticles;
    }

    private List<ArticleDto> getRecentArticleDtos() {
        List<ArticleDto> findRecentArticles = queryFactory
                .from(article)
                .leftJoin(article.newsArticles, newsArticle)
                .leftJoin(newsArticle.news, news)
                .leftJoin(news.journal, journal)
                .leftJoin(news.newsPhotoFile)
                .orderBy(article.publicationDate.desc())
                .limit(maxRecentCount)
                .transform(GroupBy.groupBy(article.id).list(
                        Projections.bean(ArticleDto.class,
                                article.id.as("articleIdx"),
                                article.title,
                                article.content,
                                article.views,
                                article.likeCount,
                                article.empathyCount,
                                article.dislikeCount,
                                article.amazingCount,
                                article.angryCount,
                                article.publicationDate,
                                Expressions.asString("RECENT").as("recommendType"),
                                GroupBy.list(Projections.bean(NewsDto.class,
                                        news.id.as("newsIdx"),
                                        news.journal.journalId,
                                        news.title,
                                        news.link,
                                        news.newsPhotoFile.fileId.as("photoFileId"),
                                        news.publicationDate
                                )).as("news")
                        )
                ));
        return findRecentArticles;
    }

    //추천 아티클 리스트 반환
    private List<ArticleDto> mergeArticles(
            List<ArticleDto> recentArticles,
            List<ArticleDto> popularArticles,
            List<ArticleDto> interestArticles,
            int pageSize) {

        Map<Long, ArticleDto> uniqueArticles = new LinkedHashMap<>();
        int recentIndex = 0, popularIndex = 0, interestIndex = 0;

        // 최신 아티클 3개마다 인기 아티클, 4개마다 관심 태그 기반 아티클 삽입
        while (uniqueArticles.size() < pageSize && recentIndex < recentArticles.size()
                || popularIndex < popularArticles.size()
                || interestIndex < interestArticles.size()) {

            // 최신 아티클 추가
            if (recentIndex < recentArticles.size()) {
                removeDuplicate(uniqueArticles, recentArticles.get(recentIndex++));
            }

            // 최신 아티클 3개마다 인기 아티클 삽입
            if (recentIndex % 2 == 0 && popularIndex < popularArticles.size()) {
                removeDuplicate(uniqueArticles, popularArticles.get(popularIndex++));
            }

            // 최신 아티클 4개마다 관심 태그 기반 아티클 삽입
            if (recentIndex % 2 == 1 && interestIndex < interestArticles.size()) {
                removeDuplicate(uniqueArticles, interestArticles.get(interestIndex++));
            }
        }
        return new ArrayList<>(uniqueArticles.values());
    }

    private void removeDuplicate(Map<Long, ArticleDto> uniqueArticles, ArticleDto article){
        if(!uniqueArticles.containsKey(article.getArticleIdx())){
            uniqueArticles.put(article.getArticleIdx(), article);
        }
    }
}

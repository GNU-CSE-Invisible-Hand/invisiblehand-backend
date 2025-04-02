package com.rrkim.module.news.repository;

import com.querydsl.core.group.Group;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rrkim.module.news.domain.Keyword;
import com.rrkim.module.news.dto.data.ArticleDto;
import com.rrkim.module.news.dto.data.KeywordDto;
import com.rrkim.module.news.dto.data.NewsDto;
import com.rrkim.module.news.dto.request.SelectArticleRequestDto;
import com.rrkim.module.news.dto.request.SelectKeywordRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.querydsl.jpa.JPAExpressions.select;
import static com.rrkim.module.news.domain.QArticle.article;
import static com.rrkim.module.news.domain.QArticleKeyword.articleKeyword;
import static com.rrkim.module.news.domain.QJournal.journal;
import static com.rrkim.module.news.domain.QNews.news;
import static com.rrkim.module.news.domain.QNewsArticle.newsArticle;
import static com.rrkim.module.news.domain.QNewsTag.newsTag;
import static com.rrkim.module.news.domain.QTag.tag;
import static com.rrkim.module.news.domain.QKeyword.keyword;

@RequiredArgsConstructor
@Repository
public class KeywordQRepository {

    private final JPAQueryFactory queryFactory;

    public Keyword find(Long keywordIdx) {
        return queryFactory
                .select(keyword)
                .from(keyword)
                .leftJoin(keyword.articleKeywords, articleKeyword).fetchJoin()
                .leftJoin(articleKeyword.article, article).fetchJoin()
                .where(
                        eqKeywordIdx(keywordIdx)
                ).fetchOne();
    }

    @Transactional
    public List<KeywordDto> find(SelectKeywordRequestDto selectKeywordRequestDto) {
        return queryFactory
                .from(keyword)
                .join(keyword.articleKeywords, articleKeyword)
                .join(articleKeyword.article, article)
                .where(
                        eqKeywordIdx(selectKeywordRequestDto.getKeywordIdx()),
                        inJournalIds(selectKeywordRequestDto.getJournalIds())
                )
                .transform(
                        groupBy(keyword.id).list(
                                Projections.bean(KeywordDto.class,
                                        keyword.id.as("keywordIdx"),
                                        keyword.content,
                                        list(
                                            Projections.bean(ArticleDto.class,
                                                    article.id.as("articleIdx"),
                                                    article.title,
                                                    article.content,
                                                    article.publicationDate
                                            )
                                        ).as("articles")
                                )
                        )
                );
    }

    private BooleanExpression eqKeywordIdx(Long keywordIdx) {
        if(keywordIdx == null) return null;
        return keyword.id.eq(keywordIdx);
    }

    private BooleanExpression inJournalIds(List<String> journalIds) {
        if(journalIds == null || journalIds.isEmpty()) { return null; }
        return article.id.in(select(article.id)
                .from(article)
                    .join(article.newsArticles, newsArticle)
                    .join(newsArticle.news, news)
                    .join(news.journal, journal)
                .where(
                        journal.journalId.in(journalIds)
                )
        );
    }

}

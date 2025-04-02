package com.rrkim.module.news.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rrkim.module.news.domain.Keyword;
import com.rrkim.module.news.dto.data.ArticleDto;
import com.rrkim.module.news.dto.data.KeywordDto;
import com.rrkim.module.news.dto.request.SelectKeywordRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.rrkim.module.news.domain.QArticle.article;
import static com.rrkim.module.news.domain.QArticleKeyword.articleKeyword;
import static com.rrkim.module.news.domain.QJournal.journal;
import static com.rrkim.module.news.domain.QKeyword.keyword;
import static com.rrkim.module.news.domain.QNews.news;
import static com.rrkim.module.news.domain.QNewsArticle.newsArticle;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    @EntityGraph(attributePaths = {"articleKeywords"})
    Keyword findKeywordById(Long keywordIdx);
}

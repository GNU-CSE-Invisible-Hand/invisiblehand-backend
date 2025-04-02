package com.rrkim.module.news.repository;

import com.rrkim.module.news.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findArticleByIdIn(List<Long> articleIdxes);
    Article findArticleById(Long articleIdx);
}
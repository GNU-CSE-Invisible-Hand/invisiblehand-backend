package com.rrkim.module.news.repository;

import com.rrkim.module.news.domain.Article;
import com.rrkim.module.news.domain.ArticleKeyword;
import com.rrkim.module.news.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleKeywordRepository extends JpaRepository<ArticleKeyword, Long> {
    List<ArticleKeyword> findArticleKeywordByKeywordAndArticleIn(Keyword keyword, List<Article> articles);
}
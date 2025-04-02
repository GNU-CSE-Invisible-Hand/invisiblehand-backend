package com.rrkim.module.news.domain;

import com.rrkim.core.common.domain.BaseEntity;
import com.rrkim.core.common.exception.UnhandledExecutionException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table
public class Keyword extends BaseEntity {

    @Column(name = "content", nullable = false)
    private String content;

    @Builder.Default
    @OneToMany(mappedBy = "keyword", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ArticleKeyword> articleKeywords = new ArrayList<>();

    public void addArticle(List<Article> articles) {
        for (Article article : articles) {
            boolean exists = articleKeywords.stream().anyMatch(ak -> ak.getKeyword().equals(this) && ak.getArticle().equals(article));
            if (exists) { continue; }

            ArticleKeyword articleKeyword = ArticleKeyword.builder()
                    .keyword(this)
                    .article(article).build();
            articleKeywords.add(articleKeyword);
        }
    }

    public void removeArticle(List<Article> articles) {
        articleKeywords.removeIf(articleKeyword -> articles.contains(articleKeyword.getArticle()));
    }
}

package com.rrkim.module.news.dto.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rrkim.core.common.util.ObjectUtility;
import com.rrkim.module.news.domain.Article;
import com.rrkim.module.news.domain.Journal;
import com.rrkim.module.news.domain.NewsArticle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDto {

    public ArticleDto(Article article) {
        this.articleIdx = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.publicationDate = article.getPublicationDate();
        this.views = article.getViews();
        this.likeCount = article.getLikeCount();
        this.empathyCount = article.getEmpathyCount();
        this.dislikeCount = article.getDislikeCount();
        this.amazingCount = article.getAmazingCount();
        this.angryCount = article.getAngryCount();

        if (Hibernate.isInitialized(article.getNewsArticles())) {
            this.news = ObjectUtility.getObjects(article.getNewsArticles(), d -> new NewsDto(d.getNews()));
        }
    }

    private Long articleIdx;

    private String title;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime publicationDate;

    private int views;

    private int likeCount;

    private int empathyCount;

    private int dislikeCount;

    private int amazingCount;

    private int angryCount;

    private List<NewsDto> news;

    private String recommendType; // RECENT, POPULAR, INTEREST 구분 값
}

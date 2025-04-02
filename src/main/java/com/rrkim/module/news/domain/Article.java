package com.rrkim.module.news.domain;

import com.rrkim.core.common.domain.BaseEntity;
import com.rrkim.module.news.constant.ReactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table
public class Article extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = BaseEntity.TEXT)
    private String content;

    @Column(name = "publication_date", nullable = false)
    private LocalDateTime publicationDate;

    @Builder.Default
    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<NewsArticle> newsArticles = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ArticleKeyword> articleKeywords = new ArrayList<>();

    @Column(name = "like_count", nullable = false)
    private int likeCount;

    @Column(name = "empathy_count", nullable = false)
    private int empathyCount;

    @Column(name = "dislike_count", nullable = false)
    private int dislikeCount;

    @Column(name = "amazing_count", nullable = false)
    private int amazingCount;

    @Column(name = "angry_count", nullable = false)
    private int angryCount;

    @Column(name = "views", nullable = false)
    private int views;

    public void addNews(List<News> newses) {
        for (News news : newses) {
            NewsArticle newsArticle = NewsArticle.builder()
                    .article(this)
                    .news(news).build();
            newsArticles.add(newsArticle);
        }
    }

    //조회수 증가
    public void incrementViews() {
        this.views++;
    }

    // 리액션 수 업데이트 (증가/감소)
    public void updateReactionCount(ReactionType reactionType, boolean isAdd) {
        int change = isAdd ? 1 : -1;

        switch (reactionType) {
            case LIKE:
                this.likeCount = Math.max(0, this.likeCount + change);
                break;
            case EMPATHY:
                this.empathyCount = Math.max(0, this.empathyCount + change);
                break;
            case DISLIKE:
                this.dislikeCount = Math.max(0, this.dislikeCount + change);
                break;
            case AMAZING:
                this.amazingCount = Math.max(0, this.amazingCount + change);
                break;
            case ANGRY:
                this.angryCount = Math.max(0, this.angryCount + change);
                break;
        }
    }

}

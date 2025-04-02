package com.rrkim.module.myinfo.domain;

import com.rrkim.core.auth.domain.User;
import com.rrkim.core.common.domain.BaseEntity;
import com.rrkim.module.news.domain.Article;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class UserReadArticle extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_idx")
    private Article article;

    @Column(name = "read_time", nullable = false)
    private LocalDateTime readTime;

    @Builder
    public UserReadArticle(User user, Article article, LocalDateTime readTime) {
        this.user = user;
        this.article = article;
        this.readTime = readTime;
    }
}


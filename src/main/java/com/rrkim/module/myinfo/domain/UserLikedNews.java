package com.rrkim.module.myinfo.domain;

import com.rrkim.core.auth.domain.User;
import com.rrkim.core.common.domain.BaseEntity;
import com.rrkim.module.news.constant.ReactionType;
import com.rrkim.module.news.domain.Article;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class UserLikedNews extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "article_idx")
    private Article article;

    @Enumerated(EnumType.STRING)
    @Column(name = "reaction_type", nullable = false)
    private ReactionType reactionType;  // 리액션 타입: LIKE, EMPATHIZE, DISLIKE, WOW, ANGRY

    @Builder
    public UserLikedNews(User user, Article article, ReactionType reactionType) {
        this.user = user;
        this.article = article;
        this.reactionType = reactionType;
    }

    public void changeReactionType(ReactionType reactionType) {
        this.reactionType = reactionType;
    }
}
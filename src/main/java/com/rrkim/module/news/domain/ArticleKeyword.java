package com.rrkim.module.news.domain;

import com.rrkim.core.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(
    uniqueConstraints = {
            @UniqueConstraint(columnNames = {"article_idx", "keyword_idx"})
    }
)
public class ArticleKeyword extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_idx")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_idx")
    private Keyword keyword;

}
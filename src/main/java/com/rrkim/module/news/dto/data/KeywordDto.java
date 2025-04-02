package com.rrkim.module.news.dto.data;

import com.rrkim.core.common.util.ObjectUtility;
import com.rrkim.module.news.domain.ArticleKeyword;
import com.rrkim.module.news.domain.Keyword;
import com.rrkim.module.news.domain.News;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class KeywordDto {

    public KeywordDto(Keyword keyword) {
        this.keywordIdx = keyword.getId();
        this.content = keyword.getContent();
        if (Hibernate.isInitialized(keyword.getArticleKeywords())) {
            this.articles = ObjectUtility.getObjects(keyword.getArticleKeywords(), d -> new ArticleDto(d.getArticle()));
        }
    }

    private Long keywordIdx;

    private String content;

    private List<ArticleDto> articles;

}

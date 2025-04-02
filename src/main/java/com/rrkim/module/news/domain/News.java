package com.rrkim.module.news.domain;

import com.rrkim.core.common.domain.BaseEntity;
import com.rrkim.core.file.domain.File;
import lombok.*;

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
public class News extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "journal_idx", nullable = false)
    private Journal journal;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "link", nullable = false, columnDefinition = BaseEntity.TEXT)
    private String link;

    @Column(name = "publication_date", nullable = false)
    private LocalDateTime publicationDate;

    @Setter
    @JoinColumn(name="news_photo_file_idx", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private File newsPhotoFile;

    @Builder.Default
    @OneToMany(mappedBy = "news", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<NewsTag> newsTags = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "news", fetch = FetchType.LAZY)
    private List<NewsArticle> newsArticles = new ArrayList<>();

    public void addNewsTag(List<Tag> tags) {
        for (Tag tag : tags) {
            NewsTag newsTag = NewsTag.builder().news(this).tag(tag).build();
            this.newsTags.add(newsTag);
        }
    }

}
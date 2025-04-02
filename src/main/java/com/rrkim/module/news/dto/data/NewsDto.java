package com.rrkim.module.news.dto.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rrkim.core.common.util.ObjectUtility;
import com.rrkim.module.news.domain.Journal;
import com.rrkim.module.news.domain.News;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import com.rrkim.core.file.domain.File;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NewsDto {

    public NewsDto(News news) {
        this.newsIdx = news.getId();
        this.title = news.getTitle();
        this.link = news.getLink();
        this.publicationDate = news.getPublicationDate();

        if (Hibernate.isInitialized(news.getNewsPhotoFile())) {
            this.photoFileId = ObjectUtility.getObject(news.getNewsPhotoFile(), File::getFileId);
        }
        if (Hibernate.isInitialized(news.getJournal())) {
            this.journalId = ObjectUtility.getObject(news.getJournal(), Journal::getJournalId);
        }
    }

    private Long newsIdx;

    private String journalId;

    private String title;

    private String link;

    private String photoFileId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime publicationDate;
}

package com.rrkim.module.news.dto.data;

import com.rrkim.core.common.util.ObjectUtility;
import com.rrkim.module.news.domain.Journal;
import com.rrkim.module.news.domain.News;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class JournalDto {

    public JournalDto(Journal journal) {
        this.journalId = journal.getJournalId();
        this.journalNm = journal.getJournalNm();
    }

    private String journalId;

    private String journalNm;
}

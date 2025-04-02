package com.rrkim.module.news.service;

import com.rrkim.core.common.exception.UnhandledExecutionException;
import com.rrkim.core.common.util.ObjectUtility;
import com.rrkim.module.news.domain.Journal;
import com.rrkim.module.news.domain.News;
import com.rrkim.module.news.domain.Tag;
import com.rrkim.module.news.dto.data.JournalDto;
import com.rrkim.module.news.dto.data.NewsDto;
import com.rrkim.module.news.dto.request.CreateJournalRequestDto;
import com.rrkim.module.news.dto.request.CreateNewsRequestDto;
import com.rrkim.module.news.repository.JournalRepository;
import com.rrkim.module.news.repository.NewsRepository;
import com.rrkim.module.news.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.List;

@Validated
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class JournalService {

    private final JournalRepository journalRepository;

    public List<JournalDto> selectJournalList() {
        return journalRepository.findAll().stream().map(JournalDto::new).toList();
    }

    @Transactional
    public JournalDto createJournal(@Valid CreateJournalRequestDto createJournalRequestDto) {
        String journalId = createJournalRequestDto.getJournalId();
        Journal findJournalId = journalRepository.findByJournalId(journalId);
        if (findJournalId != null) {
            throw new UnhandledExecutionException("news.alreadyJournalExists");
        }


        Journal journal = Journal.builder()
                .journalId(journalId)
                .journalNm(createJournalRequestDto.getJournalNm()).build();



        journalRepository.save(journal);
        return new JournalDto(journal);
    }
}
package com.rrkim.module.news.controller;

import com.rrkim.module.news.dto.data.ArticleDto;
import com.rrkim.module.news.dto.data.JournalDto;
import com.rrkim.module.news.dto.data.NewsDto;
import com.rrkim.module.news.dto.request.CreateArticleRequestDto;
import com.rrkim.module.news.dto.request.CreateJournalRequestDto;
import com.rrkim.module.news.service.JournalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "언론사 API")
public class JournalController {

    private final JournalService journalService;

    @Operation(summary = "언론사 조회", description = "언론사를 조회합니다")
    @GetMapping("/journal")
    public List<JournalDto> selectJournalList() {
        return journalService.selectJournalList();
    }

    @Operation(summary = "언론사 등록", description = "새로운 언론사를 등록합니다")
    @PostMapping("/journal")
    public JournalDto createJournal(@Valid CreateJournalRequestDto createJournalRequestDto) {
        return journalService.createJournal(createJournalRequestDto);
    }
}

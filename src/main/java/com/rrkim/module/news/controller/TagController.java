package com.rrkim.module.news.controller;

import com.rrkim.module.news.dto.data.JournalDto;
import com.rrkim.module.news.dto.data.NewsDto;
import com.rrkim.module.news.dto.data.TagDto;
import com.rrkim.module.news.dto.request.CreateNewsRequestDto;
import com.rrkim.module.news.dto.request.CreateTagRequestDto;
import com.rrkim.module.news.service.NewsService;
import com.rrkim.module.news.service.TagService;
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
@Tag(name = "태그 API")
public class TagController {

    private final TagService tagService;

    @Operation(summary = "태그 조회", description = "태그를 조회합니다")
    @GetMapping("/tag")
    public List<TagDto> selectTagList() {
        return tagService.selectTagList();
    }

    @PostMapping("/tag")
    @Operation(summary = "태그 등록", description = "새로운 태그를 등록합니다")
    public TagDto createTag(@Valid CreateTagRequestDto createTagRequestDto) {
        return tagService.createTag(createTagRequestDto);
    }
}

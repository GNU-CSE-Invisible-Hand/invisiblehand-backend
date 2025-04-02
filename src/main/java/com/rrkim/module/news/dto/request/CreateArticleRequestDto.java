package com.rrkim.module.news.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rrkim.module.news.dto.data.NewsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateArticleRequestDto {

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private String content;

    private LocalDateTime publicationDate;

    @NotNull
    @NotEmpty
    private List<Long> newsIdxes;
}

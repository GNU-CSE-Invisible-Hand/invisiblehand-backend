package com.rrkim.module.news.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SelectKeywordRequestDto {

    Long keywordIdx;

    @Builder.Default
    List<String> journalIds = new ArrayList<>();
}

package com.rrkim.module.news.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MappingArticleKeywordRequestDto {

    private Long keywordIdx;

    private List<Long> articleIdxes;

}

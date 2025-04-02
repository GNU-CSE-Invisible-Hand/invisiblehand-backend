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
public class SelectArticleRequestDto {

    Long articleIdx;

    List<String> journalIds;

    List<String> tagIds;
}

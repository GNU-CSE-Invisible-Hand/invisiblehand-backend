package com.rrkim.module.news.dto.request;

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
public class CreateJournalRequestDto {

    @NotNull
    @NotEmpty
    private String journalId;

    @NotNull
    @NotEmpty
    private String journalNm;

}

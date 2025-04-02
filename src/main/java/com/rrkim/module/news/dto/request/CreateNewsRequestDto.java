package com.rrkim.module.news.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateNewsRequestDto {

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private String link;

    @NotNull
    private String journalId;

    @NotNull
    private LocalDateTime publicationDate;

    private String photoLink;

    @NotNull
    @NotEmpty
    private List<String> tagIds;

}

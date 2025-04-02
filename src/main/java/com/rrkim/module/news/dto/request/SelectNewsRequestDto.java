package com.rrkim.module.news.dto.request;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
public class SelectNewsRequestDto {

    @Builder.Default
    @NotNull
    private List<String> journalIds = new ArrayList<>();

    @Builder.Default
    @NotNull
    private List<String> tagIds = new ArrayList<>();
}

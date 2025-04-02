package com.rrkim.module.news.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateAnswerRequestDto {

    @NotNull
    @NotEmpty
    String answerContent;

    boolean answerYn;
}

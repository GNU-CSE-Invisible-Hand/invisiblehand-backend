package com.rrkim.module.news.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateQuizRequestDto {

    @NotNull
    Long articleIdx;

    @NotNull
    @NotEmpty
    String quizContent;

    @NotNull
    @NotEmpty
    List<CreateAnswerRequestDto> answers;
}

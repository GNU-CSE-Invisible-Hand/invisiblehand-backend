package com.rrkim.module.news.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateTagRequestDto {

    @NotNull
    @NotEmpty
    @Length(max = 20)
    private String tagId;

    @NotNull
    @NotEmpty
    @Length(max = 30)
    private String TagName;
}

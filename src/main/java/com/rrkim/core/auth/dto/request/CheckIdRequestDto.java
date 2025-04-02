package com.rrkim.core.auth.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckIdRequestDto {

    @NotNull
    @NotEmpty
    private String userId;

}

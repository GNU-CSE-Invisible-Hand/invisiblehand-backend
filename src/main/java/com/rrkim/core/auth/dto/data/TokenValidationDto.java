package com.rrkim.core.auth.dto.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rrkim.core.auth.constant.TokenType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TokenValidationDto {

    boolean valid;

    String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String aud;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String tokenType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Double exp;

}

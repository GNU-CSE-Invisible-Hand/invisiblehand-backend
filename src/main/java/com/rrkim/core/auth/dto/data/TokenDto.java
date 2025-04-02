package com.rrkim.core.auth.dto.data;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {
    // 액세스/리프레시 토큰 구분
    private String accessToken;
    private String refreshToken;
}
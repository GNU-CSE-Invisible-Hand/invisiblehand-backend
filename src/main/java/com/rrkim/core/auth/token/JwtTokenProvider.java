package com.rrkim.core.auth.token;

import com.rrkim.core.auth.constant.TokenType;
import com.rrkim.core.auth.dto.data.TokenValidationDto;
import com.rrkim.core.common.util.MessageUtility;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private Key key;

    @Value("${framework-settings.token.secret-key}")
    private String secret;

    @PostConstruct
    void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication, long tokenValidityInSeconds, TokenType tokenType) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + (tokenValidityInSeconds * 1000));

        return Jwts.builder()
                .setAudience(authentication.getName())
                .claim("type", tokenType)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public Claims getTokenClaims(String token) throws JwtException {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public TokenValidationDto validateToken(String token, TokenType validTokenType) {
        if(!StringUtils.hasText(token)) {
            return getErrorTokenValidationDto("auth.tokenInvalid");
        }
        String errorCode;

        try {
            Claims claims = getTokenClaims(token);
            String tokenType = claims.get("type", String.class);
            String aud = claims.get("aud", String.class);
            double exp = claims.get("exp", Double.class);

            if(
                (validTokenType == null && !TokenType.ACCESS_TOKEN.name().equals(tokenType) && !TokenType.REFRESH_TOKEN.name().equals(tokenType)) ||
                (validTokenType != null && !(validTokenType.name().equals(tokenType)))
            ) {
                errorCode = "auth.tokenInvalidRequest";
            } else {
                String message = MessageUtility.getMessage("auth.tokenValid");
                return TokenValidationDto.builder().valid(true).aud(aud).exp(exp).tokenType(tokenType).message(message).build();
            }
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException | IllegalArgumentException e) {
            errorCode = "auth.tokenInvalid";
        } catch (ExpiredJwtException e) {
            errorCode = "auth.tokenExpired";
        } catch (UnsupportedJwtException e) {
            errorCode = "auth.tokenUnsupported";
        }

        return getErrorTokenValidationDto(errorCode);
    }

    private static TokenValidationDto getErrorTokenValidationDto(String errorCode) {
        String message = MessageUtility.getMessage(errorCode);
        return TokenValidationDto.builder().valid(false).message(message).build();
    }

}
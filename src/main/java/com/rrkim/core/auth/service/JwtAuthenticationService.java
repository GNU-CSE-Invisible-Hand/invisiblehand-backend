package com.rrkim.core.auth.service;

import com.rrkim.core.auth.constant.TokenType;
import com.rrkim.core.auth.domain.User;
import com.rrkim.core.auth.dto.data.TokenDto;
import com.rrkim.core.auth.dto.data.TokenValidationDto;
import com.rrkim.core.auth.repository.UserRepository;
import com.rrkim.core.auth.security.AuthenticationUserDetails;
import com.rrkim.core.auth.token.JwtTokenProvider;
import com.rrkim.core.auth.dto.request.SignInRequestDto;
import com.rrkim.core.common.exception.UnhandledExecutionException;
import com.rrkim.core.common.util.HashMapCreator;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.aspectj.weaver.patterns.IToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class JwtAuthenticationService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;

    //각 토큰 유효기간 추가
    @Value("${framework-settings.token.access-token-validity-in-seconds}")
    private long accessTokenValidityInSeconds;

    @Value("${framework-settings.token.refresh-token-validity-in-seconds}")
    private long refreshTokenValidityInSeconds;

    public TokenDto createToken(SignInRequestDto signInRequestDto) {
        String requestRefreshToken = signInRequestDto.getRefreshToken();
        Authentication authentication;

        if(requestRefreshToken != null) {
            TokenValidationDto tokenValidationDto = validateToken(requestRefreshToken, TokenType.REFRESH_TOKEN);
            String aud = tokenValidationDto.getAud();
            if(!tokenValidationDto.isValid()) {
                throw new UnhandledExecutionException("auth.refreshTokenInvalid");
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(aud);
            authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        } else {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signInRequestDto.getUserId(), signInRequestDto.getPassword());
            authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtTokenProvider.createToken(authentication, accessTokenValidityInSeconds, TokenType.ACCESS_TOKEN);
        String refreshToken = jwtTokenProvider.createToken(authentication, refreshTokenValidityInSeconds, TokenType.REFRESH_TOKEN);

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = jwtTokenProvider.getTokenClaims(token);
        String userId = claims.getAudience();

        User user = userRepository.findByUserIdWithUserRolesUsingFetchJoin(userId);
        if(user == null) {
            throw new UnhandledExecutionException("auth.userNotFound");
        }

        AuthenticationUserDetails authenticationUserDetails = new AuthenticationUserDetails(user);
        Collection<? extends GrantedAuthority> authorities = authenticationUserDetails.getAuthorities();
        return new UsernamePasswordAuthenticationToken(authenticationUserDetails, token, authorities);
    }

    public TokenValidationDto validateToken(String token, TokenType tokenType) {
        return jwtTokenProvider.validateToken(token, tokenType);
    }
}

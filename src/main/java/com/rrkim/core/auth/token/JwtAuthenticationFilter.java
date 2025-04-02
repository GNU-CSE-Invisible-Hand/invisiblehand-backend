package com.rrkim.core.auth.token;

import com.rrkim.core.auth.constant.TokenType;
import com.rrkim.core.auth.dto.data.TokenValidationDto;
import com.rrkim.core.auth.service.JwtAuthenticationService;
import com.rrkim.core.auth.util.AuthUtility;
import com.rrkim.core.common.util.ApiUtility;
import com.rrkim.core.common.util.HashMapCreator;
import com.rrkim.core.common.util.MessageUtility;
import com.rrkim.core.common.util.RequestUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {


    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationService jwtAuthenticationService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String token = AuthUtility.resolveToken(httpServletRequest);
        System.out.println("token = " + token);
        if(token != null && !token.isEmpty()) {
            TokenValidationDto tokenValidationDto = jwtTokenProvider.validateToken(token, TokenType.ACCESS_TOKEN);
            boolean valid = tokenValidationDto.isValid();
            System.out.println("tokenValidationDto = " + tokenValidationDto);

            if (valid) {
                Authentication authentication = jwtAuthenticationService.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }

}
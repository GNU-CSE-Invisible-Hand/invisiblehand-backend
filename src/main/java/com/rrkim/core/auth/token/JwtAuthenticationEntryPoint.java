package com.rrkim.core.auth.token;

import com.rrkim.core.common.dto.ApiResponse;
import com.rrkim.core.common.util.ApiUtility;
import com.rrkim.core.common.util.ObjectUtility;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ApiResponse messageResponse = ApiUtility.createMessageResponse(false, "auth.unauthorized");
        String json = ObjectUtility.convertJsonStringFromObject(messageResponse);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(json);
        response.flushBuffer();
    }
}
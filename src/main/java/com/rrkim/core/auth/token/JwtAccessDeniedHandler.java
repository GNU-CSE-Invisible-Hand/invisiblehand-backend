package com.rrkim.core.auth.token;

import com.rrkim.core.common.util.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ApiUtility.sendResponse(response, HashMapCreator.getStringObjectBuilder()
                .put("message", MessageUtility.getMessage("auth.forbidden")).build());
    }
}

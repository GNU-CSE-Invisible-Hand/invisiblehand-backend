package com.rrkim.core.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class RequestUtility {

    public static HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(servletRequestAttributes == null) { return null; }

        return servletRequestAttributes.getRequest();
    }

    public static String getClientIpFromRequest(HttpServletRequest httpServletRequest) {
        if(httpServletRequest == null) { return null; }
        String ip = null;

        String[] headerTypes = {"X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
        Iterator<String> headerIterator = Arrays.stream(headerTypes).iterator();
        while (headerIterator.hasNext() && ip == null) {
            ip = httpServletRequest.getHeader(headerIterator.next());
        }

        if (ip == null) { ip = httpServletRequest.getRemoteAddr(); }

        return ip;
    }

    public static String getClientIpFromRequest() {
        return getClientIpFromRequest(getHttpServletRequest());
    }
}

package com.rrkim.core.common.util;

import com.rrkim.core.common.dto.ApiResponse;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApiUtility {

    public static ApiResponse createResponse(boolean result, List<?> resultList, int resultCount) {
        return ApiResponse.builder()
                .result(result)
                .data(resultList)
                .resultCount(resultCount).build();
    }

    public static <T> ApiResponse createResponse(boolean result, T resultData) {
        List<T> resultList = new ArrayList<>();
        if(resultData != null) {
            resultList.add(resultData);
        }

        return ApiResponse.builder()
                .result(result)
                .data(resultList)
                .resultCount(resultList.size()).build();
    }

    public static ApiResponse createMessageResponse(boolean result, String messageCode) {
        Map<String, Object> resultMap = HashMapCreator.getStringObjectBuilder().put("message", MessageUtility.getMessage(messageCode)).build();
        return ApiUtility.createResponse(result, resultMap);
    }

    public static void sendResponse(ServletResponse response, Map<String, Object> resultMap) throws IOException {
        String json = ObjectUtility.convertJsonStringFromObject(ApiUtility.createResponse(false, resultMap));
        response.setContentType("application/json");
        response.getWriter().write(json);
        response.flushBuffer();
    }
}

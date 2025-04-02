package com.rrkim.core.common.controller;

import com.rrkim.core.common.dto.ApiResponse;
import com.rrkim.core.common.exception.UnhandledExecutionException;
import com.rrkim.core.common.util.HashMapCreator;
import com.rrkim.core.common.util.ApiUtility;
import com.rrkim.core.common.util.MessageUtility;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@EnableWebMvc
public class RestApiControllerAdvice {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return ResponseEntity.internalServerError().body(getExceptionResultMap(ex, "common.noHandlerFoundException", null));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.internalServerError().body(getExceptionResultMap(ex, "common.accessDeniedException", null));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity.internalServerError().body(getExceptionResultMap(ex, "common.httpRequestMethodNotSupportedException", null));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.internalServerError().body(getExceptionResultMap(ex, "common.httpMessageNotReadableException", null));
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class, SizeLimitExceededException.class})
    public ResponseEntity<ApiResponse> handleMaxUploadSizeExceededException(Exception ex) {
        return ResponseEntity.internalServerError().body(getExceptionResultMap(ex, "common.maxUploadSizeExceededException", null));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.internalServerError().body(getExceptionResultMap(ex, "common.authenticationException", null));
    }

    @ExceptionHandler(UnhandledExecutionException.class)
    public ResponseEntity<ApiResponse> handleUnhandledExecutionException(UnhandledExecutionException ex) {
        System.out.println("ex.getMessageCode() = " + ex.getMessageCode());
        return ResponseEntity.internalServerError().body(
                getExceptionResultMap(ex, "common.runtimeException", HashMapCreator.getStringObjectBuilder()
                        .put("errorCode", ex.getMessageCode()).build())
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.internalServerError().body(getExceptionResultMap(ex, "common.runtimeException", null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleUnexpectedException(Exception ex) {
        return ResponseEntity.internalServerError().body(getExceptionResultMap(ex, "common.unexpectedException", null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(Arrays.toString(ex.getStackTrace()));
        String exception = ex.getClass().getName();

        Map<String, String> errorMap = new HashMap<>();
        List<ObjectError> objectErrorList = ex.getBindingResult().getAllErrors();
        for(ObjectError objectError : objectErrorList) {
            errorMap.put(((FieldError) objectError).getField(), objectError.getDefaultMessage());
        }

        Map<String, Object> resultMap = HashMapCreator.getStringObjectBuilder().put("exception", exception).put("error", errorMap).build();
        return ResponseEntity.badRequest().body(ApiUtility.createResponse(false, resultMap));
    }

    private ApiResponse getExceptionResultMap(Exception ex, String messageCode, Map<String, Object> paramMap) {
        ex.printStackTrace();
        String exception = ex.getClass().getName();
        String description = ex.getMessage();
        String message = null;
        if(messageCode != null) { message = MessageUtility.getMessage(messageCode); }
        if(paramMap == null) { paramMap = new HashMap<>(); }

        Map<String, Object> exceptionMap = HashMapCreator.getInstance(paramMap)
                .put("exception", exception)
                .put("message", message)
                .put("description", description).build();
        return ApiUtility.createResponse(false, exceptionMap);
    }
}

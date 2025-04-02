package com.rrkim.core.common.exception;

import com.rrkim.core.common.service.MessageSourceService;
import com.rrkim.core.common.util.MessageUtility;
import lombok.Getter;

@Getter
public class UnhandledExecutionException extends RuntimeException {

    private String messageCode;

    public UnhandledExecutionException(String messageCode, String[] arguments) {
        super(MessageUtility.getMessage(messageCode, arguments));
        this.messageCode = messageCode;
    }

    public UnhandledExecutionException(String messageCode) {
        super(MessageUtility.getMessage(messageCode, null));
        this.messageCode = messageCode;
    }

    public UnhandledExecutionException(Exception e) {
        super(e);
    }

    public UnhandledExecutionException(MessageSourceService messageSourceService, String messageCode, String[] arguments) {
        super(messageSourceService.getMessage(messageCode, arguments));
        this.messageCode = messageCode;
    }

    public UnhandledExecutionException(MessageSourceService messageSourceService, String messageCode) {
        super(messageSourceService.getMessage(messageCode));
        this.messageCode = messageCode;
    }
}

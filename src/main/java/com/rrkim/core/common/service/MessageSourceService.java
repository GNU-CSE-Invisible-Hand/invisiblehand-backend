package com.rrkim.core.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MessageSourceService {

    private final MessageSource messageSource;

    public String getMessage(String messageCode, String[] arguments) {
        return messageSource.getMessage(messageCode, arguments, LocaleContextHolder.getLocale());
    }

    public String getMessage(String messageCode) {
        return getMessage(messageCode, null);
    }
}

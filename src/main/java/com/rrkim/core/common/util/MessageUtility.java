package com.rrkim.core.common.util;

import com.rrkim.core.common.service.MessageSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageUtility {

    private static MessageSourceService messageSourceService;

    @Autowired
    public MessageUtility(MessageSourceService messageSourceService) {
        MessageUtility.messageSourceService = messageSourceService;
    }

    public static String getMessage(String messageCode, String[] arguments) {
        if(messageSourceService == null) {
            throw new NullPointerException("메시지 정보를 가져올 수 없습니다.");
        }

        return messageSourceService.getMessage(messageCode, arguments);
    }

    public static String getMessage(String messageCode) {
        return getMessage(messageCode, null);
    }
}

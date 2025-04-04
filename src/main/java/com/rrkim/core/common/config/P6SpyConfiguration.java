package com.rrkim.core.common.config;

import com.p6spy.engine.spy.P6SpyOptions;
import com.rrkim.core.common.util.P6SpySqlFormatter;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;

@Configuration
public class P6SpyConfiguration {

    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(P6SpySqlFormatter.class.getName());
    }
}

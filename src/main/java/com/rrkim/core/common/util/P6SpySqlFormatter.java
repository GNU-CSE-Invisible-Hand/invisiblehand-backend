package com.rrkim.core.common.util;

import com.p6spy.engine.common.P6Util;
import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.appender.CustomLineFormat;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.hibernate.engine.jdbc.internal.FormatStyle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class P6SpySqlFormatter implements MessageFormattingStrategy {
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        String customLogMessageFormat = P6SpyOptions.getActiveInstance().getCustomLogMessageFormat();
        String formatSql = format(category, sql);

        if (customLogMessageFormat == null) { throw new IllegalStateException("The custom log format is not defined."); }

        return customLogMessageFormat
                .replaceAll(Pattern.quote(CustomLineFormat.CONNECTION_ID), Integer.toString(connectionId))
                .replaceAll(Pattern.quote(CustomLineFormat.CURRENT_TIME), now)
                .replaceAll(Pattern.quote(CustomLineFormat.EXECUTION_TIME), Long.toString(elapsed))
                .replaceAll(Pattern.quote(CustomLineFormat.CATEGORY), category)
                .replaceAll(Pattern.quote(CustomLineFormat.EFFECTIVE_SQL), Matcher.quoteReplacement(prepared))
                .replaceAll(Pattern.quote(CustomLineFormat.EFFECTIVE_SQL_SINGLELINE), Matcher.quoteReplacement(P6Util.singleLine(prepared)))
                .replaceAll(Pattern.quote(CustomLineFormat.SQL), Matcher.quoteReplacement(formatSql))
                .replaceAll(Pattern.quote(CustomLineFormat.SQL_SINGLE_LINE), Matcher.quoteReplacement(P6Util.singleLine(sql)))
                .replaceAll(Pattern.quote(CustomLineFormat.URL), url);
    }

    private String format(String category, String sql) {
        if(sql == null || sql.trim().isEmpty() || !Category.STATEMENT.getName().equals(category)) { return sql; }

        return FormatStyle.BASIC.getFormatter().format(sql);
    }
}

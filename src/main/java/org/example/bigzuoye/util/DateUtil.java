package org.example.bigzuoye.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 当前时间字符串
     */
    public static String now() {
        return LocalDateTime.now().format(FORMATTER);
    }

    /**
     * 时间格式化
     */
    public static String format(LocalDateTime time) {
        if (time == null) {
            return null;
        }
        return time.format(FORMATTER);
    }
}

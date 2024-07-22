package Cabinet.controller;

import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final String TIME_PATTERN = "HH:mm";

    public static DateTimeFormatter getTimeFormatter() {
        return DateTimeFormatter.ofPattern(TIME_PATTERN);
    }
}

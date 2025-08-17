package ${package}.core.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for date operations
 */
public final class DateUtil {

    public static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private DateUtil() {
        // Utility class
    }

    /**
     * Format a LocalDateTime to string using default formatter
     */
    public static String format(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(DEFAULT_FORMATTER);
    }

    /**
     * Format a LocalDateTime to string using custom formatter
     */
    public static String format(LocalDateTime dateTime, DateTimeFormatter formatter) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(formatter);
    }

    /**
     * Get current timestamp as formatted string
     */
    public static String getCurrentTimestamp() {
        return format(LocalDateTime.now());
    }
}
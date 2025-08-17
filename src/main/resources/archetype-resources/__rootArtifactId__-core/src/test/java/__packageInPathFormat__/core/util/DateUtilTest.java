package ${package}.core.util;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.*;

class DateUtilTest {

    @Test
    void shouldFormatDateTimeWithDefaultFormatter() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 12, 25, 15, 30, 45);
        String result = DateUtil.format(dateTime);

        assertEquals("2023-12-25 15:30:45", result);
    }

    @Test
    void shouldFormatDateTimeWithCustomFormatter() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 12, 25, 15, 30, 45);
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String result = DateUtil.format(dateTime, customFormatter);

        assertEquals("25/12/2023 15:30", result);
    }

    @Test
    void shouldReturnNullForNullDateTime() {
        String result = DateUtil.format(null);

        assertNull(result);
    }

    @Test
    void shouldReturnCurrentTimestamp() {
        String timestamp = DateUtil.getCurrentTimestamp();

        assertNotNull(timestamp);
        assertFalse(timestamp.isEmpty());
    }
}
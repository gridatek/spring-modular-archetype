package ${package}.api.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ApiResponseTest {

    @Test
    void shouldCreateSuccessResponse() {
        String data = "test data";
        ApiResponse<String> response = ApiResponse.success(data);

        assertTrue(response.isSuccess());
        assertEquals("Success", response.getMessage());
        assertEquals(data, response.getData());
        assertNotNull(response.getTimestamp());
    }

    @Test
    void shouldCreateSuccessResponseWithCustomMessage() {
        String data = "test data";
        String message = "Custom success message";
        ApiResponse<String> response = ApiResponse.success(message, data);

        assertTrue(response.isSuccess());
        assertEquals(message, response.getMessage());
        assertEquals(data, response.getData());
    }

    @Test
    void shouldCreateErrorResponse() {
        String message = "Error occurred";
        ApiResponse<String> response = ApiResponse.error(message);

        assertFalse(response.isSuccess());
        assertEquals(message, response.getMessage());
        assertNull(response.getData());
    }

    @Test
    void shouldCreateErrorResponseWithErrorCode() {
        String message = "Error occurred";
        String errorCode = "ERR_001";
        ApiResponse<String> response = ApiResponse.error(message, errorCode);

        assertFalse(response.isSuccess());
        assertEquals(message, response.getMessage());
        assertEquals(errorCode, response.getErrorCode());
        assertNull(response.getData());
    }
}
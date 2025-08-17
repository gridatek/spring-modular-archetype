package ${package}.api.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {

    @Test
    void shouldCreateUserDtoWithConstructor() {
        Long id = 1L;
        String username = "testuser";
        String email = "test@example.com";

        UserDto userDto = new UserDto(id, username, email);

        assertEquals(id, userDto.getId());
        assertEquals(username, userDto.getUsername());
        assertEquals(email, userDto.getEmail());
    }

    @Test
    void shouldSetAndGetAllFields() {
        UserDto userDto = new UserDto();

        userDto.setId(1L);
        userDto.setUsername("testuser");
        userDto.setEmail("test@example.com");
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setFullName("John Doe");

        assertEquals(1L, userDto.getId());
        assertEquals("testuser", userDto.getUsername());
        assertEquals("test@example.com", userDto.getEmail());
        assertEquals("John", userDto.getFirstName());
        assertEquals("Doe", userDto.getLastName());
        assertEquals("John Doe", userDto.getFullName());
    }
}
package ${package}.service.mapper;

import ${package}.api.dto.CreateUserRequest;
import ${package}.api.dto.UserDto;
import ${package}.core.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    void shouldConvertUserToDto() {
        // Given
        User user = new User("testuser", "test@example.com");
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");

        // When
        UserDto result = userMapper.toDto(user);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("John Doe", result.getFullName());
    }

    @Test
    void shouldReturnNullForNullUser() {
        UserDto result = userMapper.toDto(null);
        assertNull(result);
    }

    @Test
    void shouldConvertRequestToEntity() {
        // Given
        CreateUserRequest request = new CreateUserRequest("testuser", "test@example.com");
        request.setFirstName("John");
        request.setLastName("Doe");

        // When
        User result = userMapper.toEntity(request);

        // Then
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
    }

    @Test
    void shouldReturnNullForNullRequest() {
        User result = userMapper.toEntity(null);
        assertNull(result);
    }

    @Test
    void shouldUpdateEntity() {
        // Given
        User user = new User("olduser", "old@example.com");
        user.setId(1L);

        CreateUserRequest request = new CreateUserRequest("newuser", "new@example.com");
        request.setFirstName("Jane");
        request.setLastName("Smith");

        // When
        userMapper.updateEntity(user, request);

        // Then
        assertEquals("newuser", user.getUsername());
        assertEquals("new@example.com", user.getEmail());
        assertEquals("Jane", user.getFirstName());
        assertEquals("Smith", user.getLastName());
    }
}
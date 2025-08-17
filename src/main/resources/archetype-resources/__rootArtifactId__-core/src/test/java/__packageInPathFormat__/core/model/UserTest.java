package ${package}.core.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void shouldCreateUserWithUsernameAndEmail() {
        User user = new User("testuser", "test@example.com");

        assertEquals("testuser", user.getUsername());
        assertEquals("test@example.com", user.getEmail());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
    }

    @Test
    void shouldReturnFullNameWhenBothFirstAndLastNameSet() {
        User user = new User("testuser", "test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");

        assertEquals("John Doe", user.getFullName());
    }

    @Test
    void shouldReturnFirstNameWhenOnlyFirstNameSet() {
        User user = new User("testuser", "test@example.com");
        user.setFirstName("John");

        assertEquals("John", user.getFullName());
    }

    @Test
    void shouldReturnUsernameWhenNoNamesSet() {
        User user = new User("testuser", "test@example.com");

        assertEquals("testuser", user.getFullName());
    }
}
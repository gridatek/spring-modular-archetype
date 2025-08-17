package ${package}.web.controller;

import ${package}.api.contract.UserService;
import ${package}.api.dto.ApiResponse;
import ${package}.api.dto.CreateUserRequest;
import ${package}.api.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateUserSuccessfully() throws Exception {
        // Given
        CreateUserRequest request = new CreateUserRequest("testuser", "test@example.com");
        UserDto userDto = new UserDto(1L, "testuser", "test@example.com");

        when(userService.createUser(any(CreateUserRequest.class))).thenReturn(userDto);

        // When & Then
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.username").value("testuser"))
                .andExpect(jsonPath("$.data.email").value("test@example.com"));
    }

    @Test
    void shouldReturnBadRequestWhenCreatingUserWithInvalidData() throws Exception {
        // Given
        CreateUserRequest request = new CreateUserRequest("testuser", "test@example.com");

        when(userService.createUser(any(CreateUserRequest.class)))
                .thenThrow(new IllegalArgumentException("Username already exists"));

        // When & Then
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Username already exists"))
                .andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"));
    }

    @Test
    void shouldGetUserByIdSuccessfully() throws Exception {
        // Given
        Long userId = 1L;
        UserDto userDto = new UserDto(userId, "testuser", "test@example.com");

        when(userService.findById(userId)).thenReturn(Optional.of(userDto));

        // When & Then
        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.username").value("testuser"));
    }

    @Test
    void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
        // Given
        Long userId = 999L;

        when(userService.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetAllUsersSuccessfully() throws Exception {
        // Given
        UserDto user1 = new UserDto(1L, "user1", "user1@example.com");
        UserDto user2 = new UserDto(2L, "user2", "user2@example.com");

        when(userService.findAll()).thenReturn(Arrays.asList(user1, user2));

        // When & Then
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].username").value("user1"))
                .andExpect(jsonPath("$.data[1].username").value("user2"));
    }

    @Test
    void shouldUpdateUserSuccessfully() throws Exception {
        // Given
        Long userId = 1L;
        CreateUserRequest request = new CreateUserRequest("updateduser", "updated@example.com");
        UserDto updatedUser = new UserDto(userId, "updateduser", "updated@example.com");

        when(userService.updateUser(eq(userId), any(CreateUserRequest.class))).thenReturn(updatedUser);

        // When & Then
        mockMvc.perform(put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.username").value("updateduser"))
                .andExpect(jsonPath("$.data.email").value("updated@example.com"));
    }

    @Test
    void shouldDeleteUserSuccessfully() throws Exception {
        // Given
        Long userId = 1L;

        // When & Then
        mockMvc.perform(delete("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("User deleted successfully"));
    }
}
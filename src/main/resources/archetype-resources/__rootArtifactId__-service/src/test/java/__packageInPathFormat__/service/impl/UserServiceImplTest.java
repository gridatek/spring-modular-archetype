package ${package}.service.impl;

import ${package}.api.dto.CreateUserRequest;
import ${package}.api.dto.UserDto;
import ${package}.core.model.User;
import ${package}.service.mapper.UserMapper;
import ${package}.service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, userMapper);
    }

    @Test
    void shouldCreateUserSuccessfully() {
        // Given
        CreateUserRequest request = new CreateUserRequest("testuser", "test@example.com");
        User user = new User("testuser", "test@example.com");
        User savedUser = new User("testuser", "test@example.com");
        savedUser.setId(1L);
        UserDto userDto = new UserDto(1L, "testuser", "test@example.com");

        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(userMapper.toEntity(request)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.toDto(savedUser)).thenReturn(userDto);

        // When
        UserDto result = userService.createUser(request);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());

        verify(userRepository).existsByUsername("testuser");
        verify(userRepository).existsByEmail("test@example.com");
        verify(userRepository).save(user);
        verify(userMapper).toEntity(request);
        verify(userMapper).toDto(savedUser);
    }

    @Test
    void shouldThrowExceptionWhenUsernameAlreadyExists() {
        // Given
        CreateUserRequest request = new CreateUserRequest("testuser", "test@example.com");
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(request));

        assertTrue(exception.getMessage().contains("already exists"));
        verify(userRepository).existsByUsername("testuser");
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldFindUserById() {
        // Given
        Long userId = 1L;
        User user = new User("testuser", "test@example.com");
        user.setId(userId);
        UserDto userDto = new UserDto(userId, "testuser", "test@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        // When
        Optional<UserDto> result = userService.findById(userId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getId());
        assertEquals("testuser", result.get().getUsername());

        verify(userRepository).findById(userId);
        verify(userMapper).toDto(user);
    }

    @Test
    void shouldReturnEmptyWhenUserNotFound() {
        // Given
        Long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When
        Optional<UserDto> result = userService.findById(userId);

        // Then
        assertFalse(result.isPresent());
        verify(userRepository).findById(userId);
        verify(userMapper, never()).toDto(any());
    }

    @Test
    void shouldFindAllUsers() {
        // Given
        User user1 = new User("user1", "user1@example.com");
        user1.setId(1L);
        User user2 = new User("user2", "user2@example.com");
        user2.setId(2L);

        UserDto userDto1 = new UserDto(1L, "user1", "user1@example.com");
        UserDto userDto2 = new UserDto(2L, "user2", "user2@example.com");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
        when(userMapper.toDto(user1)).thenReturn(userDto1);
        when(userMapper.toDto(user2)).thenReturn(userDto2);

        // When
        List<UserDto> result = userService.findAll();

        // Then
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals("user2", result.get(1).getUsername());

        verify(userRepository).findAll();
        verify(userMapper, times(2)).toDto(any(User.class));
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        // Given
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        // When
        userService.deleteUser(userId);

        // Then
        verify(userRepository).existsById(userId);
        verify(userRepository).deleteById(userId);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentUser() {
        // Given
        Long userId = 999L;
        when(userRepository.existsById(userId)).thenReturn(false);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.deleteUser(userId));

        assertTrue(exception.getMessage().contains("not found"));
        verify(userRepository).existsById(userId);
        verify(userRepository, never()).deleteById(any());
    }
}
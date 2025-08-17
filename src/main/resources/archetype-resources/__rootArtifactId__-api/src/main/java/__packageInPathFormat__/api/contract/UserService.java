package ${package}.api.contract;

import ${package}.api.dto.CreateUserRequest;
import ${package}.api.dto.UserDto;
import java.util.List;
import java.util.Optional;

/**
 * User service contract interface
 */
public interface UserService {

    /**
     * Create a new user
     */
    UserDto createUser(CreateUserRequest request);

    /**
     * Find user by ID
     */
    Optional<UserDto> findById(Long id);

    /**
     * Find user by username
     */
    Optional<UserDto> findByUsername(String username);

    /**
     * Find all users
     */
    List<UserDto> findAll();

    /**
     * Update user
     */
    UserDto updateUser(Long id, CreateUserRequest request);

    /**
     * Delete user by ID
     */
    void deleteUser(Long id);

    /**
     * Check if user exists by username
     */
    boolean existsByUsername(String username);

    /**
     * Check if user exists by email
     */
    boolean existsByEmail(String email);
}
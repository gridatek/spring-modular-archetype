package ${package}.web.controller;

import ${package}.api.contract.UserService;
import ${package}.api.dto.ApiResponse;
import ${package}.api.dto.CreateUserRequest;
import ${package}.api.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for user management operations
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Create a new user
     */
    @PostMapping
    public ResponseEntity<ApiResponse<UserDto>> createUser(@RequestBody CreateUserRequest request) {
        try {
            logger.info("Creating user with username: {}", request.getUsername());
            UserDto user = userService.createUser(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("User created successfully", user));
        } catch (IllegalArgumentException e) {
            logger.warn("Failed to create user: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage(), "VALIDATION_ERROR"));
        } catch (Exception e) {
            logger.error("Unexpected error creating user", e);
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Internal server error", "INTERNAL_ERROR"));
        }
    }

    /**
     * Get user by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long id) {
        try {
            logger.debug("Getting user by ID: {}", id);
            Optional<UserDto> user = userService.findById(id);

            if (user.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success(user.get()));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error getting user by ID: {}", id, e);
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Internal server error", "INTERNAL_ERROR"));
        }
    }

    /**
     * Get user by username
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<ApiResponse<UserDto>> getUserByUsername(@PathVariable String username) {
        try {
            logger.debug("Getting user by username: {}", username);
            Optional<UserDto> user = userService.findByUsername(username);

            if (user.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success(user.get()));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error getting user by username: {}", username, e);
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Internal server error", "INTERNAL_ERROR"));
        }
    }

    /**
     * Get all users
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        try {
            logger.debug("Getting all users");
            List<UserDto> users = userService.findAll();
            return ResponseEntity.ok(ApiResponse.success("Users retrieved successfully", users));
        } catch (Exception e) {
            logger.error("Error getting all users", e);
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Internal server error", "INTERNAL_ERROR"));
        }
    }

    /**
     * Update user
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> updateUser(@PathVariable Long id,
                                                           @RequestBody CreateUserRequest request) {
        try {
            logger.info("Updating user with ID: {}", id);
            UserDto user = userService.updateUser(id, request);
            return ResponseEntity.ok(ApiResponse.success("User updated successfully", user));
        } catch (IllegalArgumentException e) {
            logger.warn("Failed to update user: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage(), "VALIDATION_ERROR"));
        } catch (Exception e) {
            logger.error("Unexpected error updating user with ID: {}", id, e);
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Internal server error", "INTERNAL_ERROR"));
        }
    }

    /**
     * Delete user
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        try {
            logger.info("Deleting user with ID: {}", id);
            userService.deleteUser(id);
            return ResponseEntity.ok(ApiResponse.success("User deleted successfully", null));
        } catch (IllegalArgumentException e) {
            logger.warn("Failed to delete user: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage(), "VALIDATION_ERROR"));
        } catch (Exception e) {
            logger.error("Unexpected error deleting user with ID: {}", id, e);
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Internal server error", "INTERNAL_ERROR"));
        }
    }
}
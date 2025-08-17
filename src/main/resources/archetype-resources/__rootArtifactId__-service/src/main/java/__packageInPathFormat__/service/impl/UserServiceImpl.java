package ${package}.service.impl;

import ${package}.api.contract.UserService;
import ${package}.api.dto.CreateUserRequest;
import ${package}.api.dto.UserDto;
import ${package}.core.model.User;
import ${package}.service.mapper.UserMapper;
import ${package}.service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of UserService interface
 */
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto createUser(CreateUserRequest request) {
        logger.info("Creating user with username: {}", request.getUsername());

        if (existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("User with username '" + request.getUsername() + "' already exists");
        }

        if (existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("User with email '" + request.getEmail() + "' already exists");
        }

        User user = userMapper.toEntity(request);
        User savedUser = userRepository.save(user);

        logger.info("Successfully created user with ID: {}", savedUser.getId());
        return userMapper.toDto(savedUser);
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        logger.debug("Finding user by ID: {}", id);
        return userRepository.findById(id)
                .map(userMapper::toDto);
    }

    @Override
    public Optional<UserDto> findByUsername(String username) {
        logger.debug("Finding user by username: {}", username);
        return userRepository.findByUsername(username)
                .map(userMapper::toDto);
    }

    @Override
    public List<UserDto> findAll() {
        logger.debug("Finding all users");
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(Long id, CreateUserRequest request) {
        logger.info("Updating user with ID: {}", id);

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));

        // Check if username is being changed and if it conflicts with existing user
        if (!existingUser.getUsername().equals(request.getUsername()) &&
                existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username '" + request.getUsername() + "' is already taken");
        }

        // Check if email is being changed and if it conflicts with existing user
        if (!existingUser.getEmail().equals(request.getEmail()) &&
                existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email '" + request.getEmail() + "' is already taken");
        }

        userMapper.updateEntity(existingUser, request);
        User updatedUser = userRepository.save(existingUser);

        logger.info("Successfully updated user with ID: {}", id);
        return userMapper.toDto(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        logger.info("Deleting user with ID: {}", id);

        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }

        userRepository.deleteById(id);
        logger.info("Successfully deleted user with ID: {}", id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}


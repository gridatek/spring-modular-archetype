package ${package}.service.mapper;

import ${package}.api.dto.CreateUserRequest;
import ${package}.api.dto.UserDto;
import ${package}.core.model.User;

/**
 * Mapper for converting between User entities and DTOs
 */
public class UserMapper {

    /**
     * Convert User entity to UserDto
     */
    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setFullName(user.getFullName());

        return dto;
    }

    /**
     * Convert CreateUserRequest to User entity
     */
    public User toEntity(CreateUserRequest request) {
        if (request == null) {
            return null;
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        return user;
    }

    /**
     * Update existing User entity with data from CreateUserRequest
     */
    public void updateEntity(User user, CreateUserRequest request) {
        if (user == null || request == null) {
            return;
        }

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.updateTimestamp();
    }
}
package ${package}.web.config;

import ${package}.api.contract.UserService;
import ${package}.service.impl.UserServiceImpl;
import ${package}.service.mapper.UserMapper;
import ${package}.service.repository.UserRepository;
import ${package}.service.repository.impl.InMemoryUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for service layer beans
 */
@Configuration
public class ServiceConfiguration {

    @Bean
    public UserRepository userRepository() {
        return new InMemoryUserRepository();
    }

    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }

    @Bean
    public UserService userService(UserRepository userRepository, UserMapper userMapper) {
        return new UserServiceImpl(userRepository, userMapper);
    }
}
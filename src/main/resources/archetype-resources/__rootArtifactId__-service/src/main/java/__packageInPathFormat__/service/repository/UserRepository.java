package ${package}.service.repository;

import ${package}.core.model.User;
import java.util.List;
import java.util.Optional;

/**
 * User repository interface for data access operations
 */
public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    boolean existsById(Long id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void deleteById(Long id);

    void delete(User user);

    long count();
}
package ${package}.service.repository.impl;

import ${package}.core.model.User;
import ${package}.service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * In-memory implementation of UserRepository for demonstration purposes
 */
public class InMemoryUserRepository implements UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(idGenerator.getAndIncrement());
            logger.debug("Assigned new ID {} to user", user.getId());
        } else {
            user.updateTimestamp();
        }

        users.put(user.getId(), user);
        logger.debug("Saved user with ID: {}", user.getId());
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = users.get(id);
        logger.debug("Found user by ID {}: {}", id, user != null);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<User> user = users.values().stream()
                .filter(u -> Objects.equals(u.getUsername(), username))
                .findFirst();
        logger.debug("Found user by username {}: {}", username, user.isPresent());
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> user = users.values().stream()
                .filter(u -> Objects.equals(u.getEmail(), email))
                .findFirst();
        logger.debug("Found user by email {}: {}", email, user.isPresent());
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> allUsers = new ArrayList<>(users.values());
        logger.debug("Retrieved {} users", allUsers.size());
        return allUsers;
    }

    @Override
    public boolean existsById(Long id) {
        boolean exists = users.containsKey(id);
        logger.debug("User exists by ID {}: {}", id, exists);
        return exists;
    }

    @Override
    public boolean existsByUsername(String username) {
        boolean exists = users.values().stream()
                .anyMatch(u -> Objects.equals(u.getUsername(), username));
        logger.debug("User exists by username {}: {}", username, exists);
        return exists;
    }

    @Override
    public boolean existsByEmail(String email) {
        boolean exists = users.values().stream()
                .anyMatch(u -> Objects.equals(u.getEmail(), email));
        logger.debug("User exists by email {}: {}", email, exists);
        return exists;
    }

    @Override
    public void deleteById(Long id) {
        User removed = users.remove(id);
        logger.debug("Deleted user by ID {}: {}", id, removed != null);
    }

    @Override
    public void delete(User user) {
        if (user.getId() != null) {
            deleteById(user.getId());
        }
    }

    @Override
    public long count() {
        long count = users.size();
        logger.debug("Total user count: {}", count);
        return count;
    }
}



package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private Map<Integer, User> repository;
    private AtomicInteger counter = new AtomicInteger(0);
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    /*{
        this.save(new User(1, "Admin", "admin@user.com", "password", Role.ROLE_ADMIN, null));
        this.save(new User(2, "User", "email@user.com", "password", Role.ROLE_USER, null));
    }*/

    public InMemoryUserRepositoryImpl() {
        repository = new ConcurrentHashMap<Integer, User>(){{
            put(1, new User(1, "Admin", "admin@user.com", "password", Role.ROLE_ADMIN, Role.ROLE_ADMIN));
            put(2, new User(2, "User", "email@user.com", "password", Role.ROLE_USER, Role.ROLE_USER));
        }};
    }

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);

        if (repository.get(id)==null) return false;
        repository.remove(id);
        return true;
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        repository.put(counter.incrementAndGet(), user);
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        return repository.values()
                .stream()
                .sorted((user1, user2) -> user1.getName().compareTo(user2.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        return repository.values()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
}

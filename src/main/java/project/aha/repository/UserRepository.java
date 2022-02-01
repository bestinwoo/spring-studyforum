package project.aha.repository;

import project.aha.domain.User;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    List<User> findAll();
}

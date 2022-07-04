package project.aha.user.repository;

import java.util.List;
import java.util.Optional;

import project.aha.user.domain.User;

public interface UserRepository {
	User save(User user);

	Optional<User> findById(Long id);

	Optional<User> findByEmail(String email);

	List<User> findAll();
}

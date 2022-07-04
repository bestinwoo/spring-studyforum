package project.aha.user.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import project.aha.user.domain.User;

@Mapper
public interface UserMapper {
	Long save(User user);

	Optional<User> findById(Long id);

	Optional<User> findByLoginId(String id);

	List<User> findAll();

	int delete(Long id);
}

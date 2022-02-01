package project.aha.repository;

import org.apache.ibatis.annotations.Mapper;
import project.aha.domain.User;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
    Long save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    int delete(Long id);
}

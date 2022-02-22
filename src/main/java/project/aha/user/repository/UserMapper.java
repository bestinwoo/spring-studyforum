package project.aha.user.repository;

import org.apache.ibatis.annotations.Mapper;
import project.aha.user.domain.User;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
    Long save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findBySocialId(String socialId);
    List<User> findAll();
    int delete(Long id);
}

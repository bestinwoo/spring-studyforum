package project.aha.service;

import org.springframework.stereotype.Service;
import project.aha.domain.User;
import project.aha.repository.UserMapper;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 회원가입
     */
    public Long join(User user) {
        validateDuplicateUser(user);
        userMapper.save(user);
        return user.getId();
    }

    /**
     * 이메일 중복 체크
     */
    private void validateDuplicateUser(User user) {
        userMapper.findByEmail(user.getEmail()).ifPresent(m -> {
            throw new IllegalStateException("이메일 중복");
        });
    }

    public List<User> findUsers() {
        return userMapper.findAll();
    }

    public Optional<User> findOne(Long userId) {
        return userMapper.findById(userId);
    }

    public int delete(Long userId) {
        return userMapper.delete(userId);
    }

}

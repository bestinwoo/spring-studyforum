package project.aha.service;

import org.springframework.stereotype.Service;
import project.aha.domain.User;
import project.aha.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 회원가입
     */
    public Long join(User user) {
        validateDuplicateUser(user);

        userRepository.save(user);
        return user.getId();
    }

    /**
     * 이메일 중복 체크
     */
    private void validateDuplicateUser(User user) {
        userRepository.findByEmail(user.getEmail()).ifPresent(m -> {
            throw new IllegalStateException("이메일 중복");
        });
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findOne(Long userId) {
        return userRepository.findById(userId);
    }

}

package project.aha.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.aha.domain.User;
import project.aha.repository.UserRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class UserServiceIntegrationTest {
    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

    @Test
    void 회원가입() {
        //given
        User user = new User();
        user.setEmail("bestinwoo@gmail.com");
        user.setNickname("bestinu");
        user.setPassword("1111");
        user.setRegister_date(LocalDateTime.now());
        //when
        Long saveId = userService.join(user);
        //then
        User findUser = userService.findOne(saveId).get();
        assertThat(user.getNickname()).isEqualTo(findUser.getNickname());
    }

    @Test
    void 이메일_중복_체크() {
        //given
        User user1 = new User();
        user1.setEmail("test");
        user1.setNickname("test");
        user1.setPassword("1111");
        user1.setRegister_date(LocalDateTime.now());

        User user2 = new User();
        user2.setEmail("test");
        user2.setNickname("test");
        user2.setPassword("1111");
        user2.setRegister_date(LocalDateTime.now());

        //when
        userService.join(user1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> userService.join(user2));

        assertThat(e.getMessage()).isEqualTo("이메일 중복");
    }

}
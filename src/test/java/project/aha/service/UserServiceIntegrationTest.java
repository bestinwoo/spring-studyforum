package project.aha.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.aha.user.domain.MemberProvider;
import project.aha.user.domain.User;
import project.aha.user.service.UserService;


import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class UserServiceIntegrationTest {
    @Autowired
    UserService userService;

    @Test
    void 회원가입() {
        //given
        User user = User.builder().build();

        user.setEmail("bestinwoo@gmail.com");
        user.setNickname("bestinu");
        user.setPassword("1111");
        user.setMemberProvider(MemberProvider.GENERAL);
        user.setRegisterDate(LocalDateTime.now());

        //when
        Long saveId = userService.join(user);
        //then
        User findUser = userService.findOne(saveId).get();


        assertThat(user.getNickname()).isEqualTo(findUser.getNickname());
    }

    @Test
    void 이메일_중복_체크() {
        //given
        User user1 = User.builder().build();
        user1.setEmail("test");
        user1.setNickname("test");
        user1.setPassword("1111");
        user1.setMemberProvider(MemberProvider.GENERAL);
        user1.setRegisterDate(LocalDateTime.now());

        User user2 = User.builder().build();
        user2.setEmail("test");
        user2.setNickname("test");
        user2.setPassword("1111");
        user2.setMemberProvider(MemberProvider.GENERAL);
        user2.setRegisterDate(LocalDateTime.now());

        //when
        userService.join(user1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> userService.join(user2));

        assertThat(e.getMessage()).isEqualTo("이메일 중복");
    }

    @Test
    void 회원_삭제() {
        //given
        User user = User.builder().build();

        user.setEmail("bestinwoo@gmail.com");
        user.setNickname("bestinu");
        user.setPassword("1111");
        user.setMemberProvider(MemberProvider.GENERAL);
        user.setRegisterDate(LocalDateTime.now());
        //when
        Long saveId = userService.join(user);
        userService.delete(saveId);
        //then
        assertThat(userService.findOne(saveId)).isEmpty();
    }


}
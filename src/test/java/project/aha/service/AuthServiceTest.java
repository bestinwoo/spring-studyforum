package project.aha.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import project.aha.auth.dto.AuthRequest;
import project.aha.auth.dto.AuthResponse;
import project.aha.auth.dto.TokenDto;
import project.aha.auth.service.AuthService;
import project.aha.user.domain.User;
import project.aha.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
	@InjectMocks
	AuthService authService;
	@Mock
	UserRepository userRepository;
	@Spy
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	@Mock
	AuthenticationManagerBuilder authenticationManagerBuilder;

	@Nested
	@DisplayName("회원가입")
	class Signup {

		private AuthRequest authRequest;

		@BeforeEach
		void setup() {
			authRequest = new AuthRequest("test", "1234");
		}

		@Test
		@DisplayName("회원가입 성공")
		void joinUser() {
			//given
			User user = authRequest.toUser(encoder);
			when(userRepository.save(any(User.class))).thenReturn(user);

			//when
			AuthResponse response = authService.signup(authRequest);

			//then
			assertThat(response.getLoginId()).isEqualTo(user.getLoginId());
		}
	}

	@Nested
	@DisplayName("로그인")
	class Login {
		private AuthRequest authRequest;

		@BeforeEach
		void setup() {
			authRequest = new AuthRequest("test@te1s2t.com", "1111");
		}

		@Test
		@DisplayName("로그인 성공")
		void login_success() {
			// given

			when(authenticationManagerBuilder.getObject()
				.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(
				SecurityContextHolder.getContext().getAuthentication());
			// when
			TokenDto login = authService.login(authRequest);
			// then
			System.out.println("login = " + login);
		}
	}
}

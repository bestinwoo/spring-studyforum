package project.aha.auth;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import project.aha.auth.dto.AuthRequest;

public class AuthDtoTest {
	private static ValidatorFactory factory;
	private static Validator validator;

	@BeforeAll
	public static void init() {
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@AfterAll
	public static void close() {
		factory.close();
	}

	@Nested
	@DisplayName("아이디 검증")
	class IdTest {
		@Test // TODO: validation 그룹화해서 테스트 분리하기
		@DisplayName("아이디 형식이 맞지 않으면 에러 발생")
		void id_pattern_validate_fail() {
			//given
			AuthRequest authRequest = new AuthRequest("1aas", "123456789");
			//when
			Set<ConstraintViolation<AuthRequest>> violations = validator.validate(authRequest);
			//then
			Assertions.assertThat(violations).isNotEmpty();
		}

		@Test
		@DisplayName("아이디 형식이 맞으면 성공")
		void id_pattern_validate_success() {
			//given
			AuthRequest authRequest = new AuthRequest("fourword", "eightword");
			//when
			Set<ConstraintViolation<AuthRequest>> violations = validator.validate(authRequest);
			//then
			Assertions.assertThat(violations).isEmpty();
		}
	}

	@Nested
	@DisplayName("비밀번호 검증")
	class PasswordTest {
		@Test
		@DisplayName("패스워드가 8글자 이상일 경우 성공")
		void password_validate_success() {
			//given
			AuthRequest authRequest = new AuthRequest("test", "12345678");
			//when
			Set<ConstraintViolation<AuthRequest>> violations = validator.validate(authRequest);
			//then
			Assertions.assertThat(violations).isEmpty();
		}

		@Test
		@DisplayName("패스워드가 8글자 미만일 경우 실패")
		void password_validate_fail() {
			//given
			AuthRequest authRequest = new AuthRequest("test", "1234567");
			//when
			Set<ConstraintViolation<AuthRequest>> violations = validator.validate(authRequest);
			//then
			Assertions.assertThat(violations).isNotEmpty();
		}
	}
}


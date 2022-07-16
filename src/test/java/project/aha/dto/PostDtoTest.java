package project.aha.dto;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import project.aha.board.dto.PostDto;
import project.aha.common.validation.ValidationGroups;

public class PostDtoTest {
	private static ValidatorFactory factory;
	private static Validator validator;

	@BeforeAll
	static void init() {
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	@DisplayName("게시글 내용은 필수")
	void post_request_content_required() {
		// given
		PostDto.Request request = new PostDto.Request("", "title", 1L, null);
		// when
		Set<ConstraintViolation<PostDto.Request>> violations = validator.validate(request,
			ValidationGroups.NotEmptyGroup.class);
		// then
		assertThat(violations).isNotEmpty();
	}

	@Test
	@DisplayName("게시글 제목은 필수")
	void post_request_title_required() {
		// given
		PostDto.Request request = new PostDto.Request("content", "", 1L, null);
		// when
		Set<ConstraintViolation<PostDto.Request>> violations = validator.validate(request,
			ValidationGroups.NotEmptyGroup.class);
		// then
		assertThat(violations).isNotEmpty();
	}

	@Test
	@DisplayName("게시판 ID는 필수")
	void post_request_board_required() {
		// given
		PostDto.Request request = new PostDto.Request("content", "title", null, null);
		// when
		Set<ConstraintViolation<PostDto.Request>> violations = validator.validate(request,
			ValidationGroups.NotEmptyGroup.class);
		// then
		assertThat(violations).isNotEmpty();
	}

	@Test
	@DisplayName("모든 필수값이 있으면 성공")
	void post_request_success() {
		// given
		PostDto.Request request = new PostDto.Request("content", "title", 1L, null);
		// when
		Set<ConstraintViolation<PostDto.Request>> violations = validator.validate(request,
			ValidationGroups.NotEmptyGroup.class);
		// then
		assertThat(violations).isEmpty();
	}

}

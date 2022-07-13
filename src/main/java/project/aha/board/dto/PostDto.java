package project.aha.board.dto;

import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.aha.board.domain.Board;
import project.aha.board.domain.Post;
import project.aha.common.validation.ValidationGroups.NotEmptyGroup;
import project.aha.user.domain.User;

public class PostDto {
	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class Request {
		@NotBlank(message = "내용을 입력해주세요.", groups = NotEmptyGroup.class)
		private String content;
		@NotBlank(message = "제목을 입력해주세요.", groups = NotEmptyGroup.class)
		private String title;
		@NotNull(message = "게시판 ID는 필수입니다.", groups = NotEmptyGroup.class)
		private Long boardId;
		private Set<String> tags;

		public Post toPost(Long userId) {
			return Post.builder()
				.title(title)
				.content(content)
				.writer(User.builder().id(userId).build())
				.board(Board.builder().id(boardId).build())
				.writeDate(LocalDateTime.now())
				.views(0L)
				.replyCount(0L)
				.build();
		}
	}
}

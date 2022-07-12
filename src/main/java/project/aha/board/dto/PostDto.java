package project.aha.board.dto;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.aha.board.domain.Board;
import project.aha.board.domain.Post;
import project.aha.user.domain.User;

public class PostDto {
	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	public static class Request {
		private String content;
		private String title;
		private Long boardId;
		private Set<String> tags;

		public Post toPost(Long userId) {
			return Post.builder()
				.title(title)
				.content(content)
				.writer(User.builder().id(userId).build())
				.board(Board.builder().id(boardId).build())
				.writeDate(LocalDateTime.now())
				.build();
		}
	}

}

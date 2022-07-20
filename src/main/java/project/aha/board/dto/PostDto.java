package project.aha.board.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.aha.board.domain.Board;
import project.aha.board.domain.Post;
import project.aha.common.validation.ValidationGroups.NotEmptyGroup;
import project.aha.user.domain.User;

public class PostDto {
	@AllArgsConstructor
	@NoArgsConstructor
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

	@Getter
	@Setter
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	@Builder(access = AccessLevel.PROTECTED)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class Response {
		private Long id;
		private String title;
		private Long boardId;
		private Long writerId;
		private String writerLoginId;
		private LocalDateTime writeDate;
		private Long views;
		private Long replyCount;
		private String imagePath;
		private List<String> tags;
		private String content;

		public static Response from(Post post) {
			return Response.builder()
				.id(post.getId())
				.title(post.getTitle())
				.boardId(post.getBoard().getId())
				.writerId(post.getWriter().getId())
				.writerLoginId(post.getWriter().getLoginId())
				.writeDate(post.getWriteDate())
				.views(post.getViews())
				.replyCount(post.getReplyCount())
				.imagePath(post.getImagePath())
				.tags(post.getTags().stream().map(tag -> tag.getTag().getName()).collect(Collectors.toList()))
				.build();
		}
	}

}

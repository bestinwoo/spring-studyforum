package project.aha.board.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Getter
@Builder
public class PostResponse {
	private Long id;
	private String content;
	private String title;
	private Long views;
	private Long replyCount;
	private Long userId;
	private LocalDateTime writeDate;

	// public static PostResponse of(Post post) {
	// 	return PostResponse.builder()
	// 		.id(post.getId())
	// 		.content(post.getContent())
	// 		.title(post.getTitle())
	// 		.writeDate(post.getWriteDate())
	// 		.userId(post.getUserId())
	// 		.replyCount(post.getReplyCount())
	// 		.views(post.getViews())
	// 		.build();
	// }
}

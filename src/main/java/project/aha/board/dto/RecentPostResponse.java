package project.aha.board.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.aha.board.domain.RecentPost;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecentPostResponse {
	private Long boardId;
	private Long postId;
	private String boardTitle;
	private String postTitle;
	private String writerLoginId;
	private LocalDateTime writeDate;
	private Long views;
	private Long replyCount;

	public static RecentPostResponse of(RecentPost post) {
		return RecentPostResponse.builder()
			.boardId(post.getBoard().getId())
			.postId(post.getPostId())
			.boardTitle(post.getBoard().getTitle())
			.postTitle(post.getPostTitle())
			.writerLoginId(post.getWriter().getLoginId())
			.writeDate(post.getWriteDate())
			.views(post.getViews())
			.replyCount(post.getReplyCount())
			.build();
	}
}

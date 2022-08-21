package project.aha.board.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	private String imagePath;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime writeDate;
	private Long views;
	private List<String> tags;
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
			.tags(post.getTags().stream().map(tag -> tag.getTag().getName()).collect(Collectors.toList()))
			.imagePath(post.getImagePath())
			.build();
	}
}

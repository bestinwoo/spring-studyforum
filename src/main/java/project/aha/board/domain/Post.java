package project.aha.board.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Post {
	private Long id;
	private String content;
	private String title;
	private Long userId;
	private Long boardId;
	private Long views;
	private Long replyCount;
	private LocalDateTime writeDate;
	private String writer;
}

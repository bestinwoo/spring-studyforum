package project.aha.board.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

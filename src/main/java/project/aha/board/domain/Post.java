package project.aha.board.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Post {
    private Long id;
    private String content;
    private String title;
    private Long userId;
    private Long boardId;
    private Long views;
    private LocalDateTime writeDate;
}

package project.aha.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class post {
    private Long id;
    private String content;
    private String title;
    private Long userId;
    private Long boardId;
    private LocalDateTime writeDate;
}

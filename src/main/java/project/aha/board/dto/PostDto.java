package project.aha.board.dto;

import lombok.*;
import project.aha.board.domain.Post;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
public class PostDto {
    private String content;
    private String title;
    private Long userId;
    private Long boardId;
    private String writer;

    public Post toPost() {
        return Post.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .boardId(boardId)
                .writer(writer)
                .build();
    }


}

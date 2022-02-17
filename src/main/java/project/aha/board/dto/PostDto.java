package project.aha.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import project.aha.board.domain.Post;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {
    private String content;
    private String title;
    private Long userId;
    private Long boardId;

    public Post toPost(PostDto postDto) {
        return Post.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .boardId(boardId)
                .build();
    }

}

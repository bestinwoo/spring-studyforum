package project.aha.board.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.aha.board.domain.Post;

import java.time.LocalDateTime;


@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Getter
@Builder
public class PostResponse {
    private String content;
    private String title;
    private String writer;
    private LocalDateTime writeDate;

    public static PostResponse of(Post post) {
        return PostResponse.builder()
                .content(post.getContent())
                .title(post.getTitle())
                .writer(post.getWriter())
                .writeDate(post.getWriteDate())
                .build();
    }
}

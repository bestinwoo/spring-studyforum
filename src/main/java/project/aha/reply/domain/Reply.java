package project.aha.reply.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
    private Long id;
    private Long postId;
    private String comment;
    private LocalDateTime writeDate;
    private Long userId;
    private String writer;
}

package project.aha.board.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface PostResponse {
	Long getId();

	String getTitle();

	Long getBoardId();

	Long getWriterId();

	String getWriterLoginId();

	LocalDateTime getWriteDate();

	Long getViews();

	Long getReplyCount();

	String getImagePath();

}

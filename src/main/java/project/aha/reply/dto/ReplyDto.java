package project.aha.reply.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.aha.board.domain.Post;
import project.aha.reply.domain.Reply;
import project.aha.user.domain.User;

public class ReplyDto {
	@Getter
	public static class Request {
		private String comment;
		private Long postId;

		public Reply toReply(Long userId) {
			return Reply.builder()
				.comment(this.comment)
				.post(Post.builder()
					.id(postId)
					.build())
				.writer(User.builder().id(userId).build())
				.writeDate(LocalDateTime.now())
				.build();
		}

	}

	@Getter
	@Setter
	@Builder
	public static class Response {
		private Long id;
		private Long postId;
		private String postWriterLoginId;
		private String comment;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
		private LocalDateTime writeDate;
		private String writerLoginId;
		private Long writerId;
		private String writerImagePath;

		public static Response of(Reply reply) {
			return Response.builder()
				.id(reply.getId())
				.postId(reply.getPost().getId())
				.postWriterLoginId(reply.getPost().getWriter().getLoginId())
				.comment(reply.getComment())
				.writeDate(reply.getWriteDate())
				.writerLoginId(reply.getWriter() != null ? reply.getWriter().getLoginId() : null)
				.writerId(reply.getWriter() != null ? reply.getWriter().getId() : null)
				.writerImagePath(reply.getWriter() != null ? reply.getWriter().getProfileImgPath() : null)
				.build();
		}
	}

}

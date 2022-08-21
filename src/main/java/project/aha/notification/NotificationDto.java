package project.aha.notification;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import project.aha.board.domain.Post;
import project.aha.user.domain.User;

public class NotificationDto {
	@Getter
	@Builder
	public static class Request {
		private Long receiverId;
		private Long postId;
		private String message;

		public Notification toNotification() {
			return Notification.builder()
				.post(Post.builder().id(postId).build())
				.publishDate(LocalDateTime.now())
				.receiver(User.builder().id(receiverId).build())
				.viewYn(false)
				.message(message)
				.build();

		}
	}

	@Getter
	@Builder
	public static class Response {
		private Long id;
		private Long postId;
		private String message;
		private boolean viewYn;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
		private LocalDateTime publishDate;

		public static Response of(Notification notification) {
			return Response.builder()
				.id(notification.getId())
				.message(notification.getMessage())
				.publishDate(notification.getPublishDate())
				.postId(notification.getPost().getId())
				.viewYn(notification.isViewYn())
				.build();
		}
	}
}

package project.aha.notification;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.aha.common.dto.BasicResponse;
import project.aha.common.dto.Result;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {
	private final NotificationService notificationService;

	@GetMapping("/{userId}")
	public ResponseEntity<BasicResponse> getNotifications(@PathVariable Long userId) {
		return ResponseEntity.ok(new Result<>(notificationService.getNotifications(userId)));
	}

	@PostMapping("/{notificationId}")
	public ResponseEntity<BasicResponse> readNotification(@PathVariable Long notificationId) {
		notificationService.readNotification(notificationId);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{notificationId}")
	public ResponseEntity<BasicResponse> deleteNotification(@PathVariable Long notificationId) {
		notificationService.deleteNotification(notificationId);
		return ResponseEntity.ok().build();
	}
}

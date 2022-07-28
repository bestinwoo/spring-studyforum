package project.aha.notification;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class NotificationService {
	private final NotificationRepository notificationRepository;
	private final SimpMessageSendingOperations messagingTemplate;

	@Transactional(readOnly = true)
	public void sendNotification(NotificationDto.Request request) {
		Notification notification = request.toNotification();
		notificationRepository.save(notification);
		messagingTemplate.convertAndSend("/topic/notify/" + notification.getReceiver().getId(),
			NotificationDto.Response.of(notification));
	}
}

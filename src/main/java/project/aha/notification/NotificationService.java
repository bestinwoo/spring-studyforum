package project.aha.notification;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.aha.common.ResourceNotFoundException;

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

	@Transactional(readOnly = true)
	public List<NotificationDto.Response> getNotifications(Long userId) {
		return notificationRepository.findByReceiverId(userId)
			.stream()
			.map(NotificationDto.Response::of)
			.collect(Collectors.toList());
	}

	public void readNotification(Long notificationId) {
		Notification notification = notificationRepository.findById(notificationId)
			.orElseThrow(ResourceNotFoundException::new);
		notification.readNotification();
	}
}

package project.aha.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import project.aha.auth.jwt.TokenProvider;

@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {
	private final TokenProvider tokenProvider;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		if (accessor.getCommand() == StompCommand.CONNECT) {
			if (!tokenProvider.validateToken(accessor.getFirstNativeHeader("Authorization"))) {
				throw new AccessDeniedException("권한이 없습니다.");
			}
		}
		return message;
	}
}

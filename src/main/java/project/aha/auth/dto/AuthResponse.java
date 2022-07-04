package project.aha.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.aha.user.domain.User;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AuthResponse {
	private String loginId;

	public static AuthResponse of(User user) {
		return new AuthResponse(user.getLoginId());
	}
}

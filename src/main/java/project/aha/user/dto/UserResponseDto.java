package project.aha.user.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import project.aha.user.domain.Role;
import project.aha.user.domain.User;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class UserResponseDto {
	private String loginId;
	private String nickname;
	private String address;
	private LocalDateTime registerDate;
	private Role role; // TODO: 이거 role_id -> role 가져오는거 해야함
	private String profileImagePath;

	public static UserResponseDto of(User user) {
		return UserResponseDto.builder()
			.loginId(user.getLoginId())
			.registerDate(user.getRegisterDate())
			.role(Role.MEMBER)
			.profileImagePath(user.getProfileImgPath())
			.build();
	}
}

package project.aha.user.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.aha.user.domain.Role;
import project.aha.user.domain.User;

public class UserDto {
	@Getter
	@Setter
	//@JsonInclude(JsonInclude.Include.NON_NULL)
	@Builder
	public static class Response {
		private Long id;
		private String loginId;
		private LocalDateTime registerDate;
		private Role role; // TODO: 이거 role_id -> role 가져오는거 해야함
		private String profileImagePath;
		private String introduce;

		public static Response of(User user) {
			return Response.builder()
				.id(user.getId())
				.loginId(user.getLoginId())
				.registerDate(user.getRegisterDate())
				.role(Role.MEMBER)
				.profileImagePath(user.getProfileImgPath())
				.introduce(user.getIntroduce())
				.build();
		}
	}

	@Getter
	@Setter
	public static class ModifyIntroduce {
		private String introduce;
	}

}

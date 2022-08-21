package project.aha.user.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.aha.user.domain.Role;
import project.aha.user.domain.User;

public class UserDto {
	@Getter
	@Setter
	@Builder
	public static class Response {
		private Long id;
		private String loginId;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
		private LocalDateTime registerDate;
		private Role role;
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

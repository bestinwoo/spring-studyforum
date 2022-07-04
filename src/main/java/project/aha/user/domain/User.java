package project.aha.user.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	private Long id;
	private String loginId;
	private String password;
	private LocalDateTime registerDate;
	private LocalDateTime leaveDate;
	private Long roleId;
	private String profileImagePath;
}



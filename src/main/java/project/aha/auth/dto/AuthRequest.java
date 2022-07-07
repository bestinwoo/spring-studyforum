package project.aha.auth.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.aha.user.domain.MemberRole;
import project.aha.user.domain.User;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
	@Pattern(regexp = "^[a-zA-Z]+[a-zA-Z0-9_]{3,14}$", message = "아이디 형식이 틀립니다.")
	private String id;
	@Size(message = "비밀번호는 8글자 이상, 20글자 이하입니다.", min = 8, max = 20)
	private String password;

	public User toUser(PasswordEncoder passwordEncoder) {
		return User.builder()
			.loginId(id)
			.password(passwordEncoder.encode(password))
			.registerDate(LocalDateTime.now())
			.role(MemberRole.builder().id(2L).build())
			.build();
	}

	public UsernamePasswordAuthenticationToken toAuthentication() {
		return new UsernamePasswordAuthenticationToken(id, password);
	}
}

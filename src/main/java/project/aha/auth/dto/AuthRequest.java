package project.aha.auth.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.aha.user.domain.User;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
	private String id;
	private String password;

	public User toUser(PasswordEncoder passwordEncoder) {
		return User.builder()
			.loginId(id)
			.password(passwordEncoder.encode(password))
			.roleId(2L)
			.build();
	}

	public UsernamePasswordAuthenticationToken toAuthentication() {
		return new UsernamePasswordAuthenticationToken(id, password);
	}
}

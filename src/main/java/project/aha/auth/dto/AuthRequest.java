package project.aha.auth.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
	private String id;
	private String password;

	public User toUser(PasswordEncoder passwordEncoder) {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("");
		String format = now.format(dateTimeFormatter);
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

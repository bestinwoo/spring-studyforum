package project.aha.auth.service;

import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.aha.user.domain.Role;
import project.aha.user.domain.User;
import project.aha.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByLoginId(username).map(this::createUserDetails)
			.orElseThrow(() -> new UsernameNotFoundException(username + "는 데이터베이스에 없는 데이터입니다."));
	}

	private UserDetails createUserDetails(User user) {
		String role = "ROLE_USER";
		if (user.getRole().getId() == 1) {
			role = Role.ADMIN.toString();
		} else if (user.getRole().getId() == 2) {
			role = Role.MEMBER.toString();
		}
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);

		return new org.springframework.security.core.userdetails.User(
			String.valueOf(user.getId()),
			user.getPassword(),
			Collections.singleton(grantedAuthority)
		);
	}
}

package project.aha.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.aha.user.domain.Role;
import project.aha.user.domain.User;
import project.aha.user.repository.UserMapper;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userMapper.findByEmail(username).map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(username + "는 데이터베이스에 없는 데이터입니다."));
    }

    private UserDetails createUserDetails(User user) {
        String role = "ROLE_USER";
        if (user.getRoleId() == 1) {
            role = Role.ADMIN.toString();
        } else if (user.getRoleId() == 2) {
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

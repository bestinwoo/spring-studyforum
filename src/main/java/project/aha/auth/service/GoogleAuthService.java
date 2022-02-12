package project.aha.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.aha.auth.client.ClientGoogle;
import project.aha.auth.dto.AuthRequest;
import project.aha.auth.dto.AuthResponse;
import project.aha.auth.dto.TokenDto;
import project.aha.auth.jwt.TokenProvider;
import project.aha.domain.User;
import project.aha.repository.UserMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoogleAuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final ClientGoogle clientGoogle;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public TokenDto login(AuthRequest authRequest) {
        User googleUser = clientGoogle.getUserData(authRequest.getAccessToken());
        String email = googleUser.getEmail();
        Optional<User> user = userMapper.findByEmail(email);


        if (user.isEmpty()) {
            // 회원정보 없음 리턴해주기
            userMapper.save(googleUser);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, passwordEncoder.encode("1234"));
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        return tokenDto;
    }
}

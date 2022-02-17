package project.aha.auth.service;

import lombok.RequiredArgsConstructor;
import org.apache.el.parser.Token;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.aha.auth.domain.RefreshToken;
import project.aha.auth.dto.*;
import project.aha.auth.jwt.TokenProvider;
import project.aha.auth.repository.RefreshTokenMapper;
import project.aha.domain.User;
import project.aha.repository.UserMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserMapper userMapper;
    private final RefreshTokenMapper refreshTokenMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthResponse signup(AuthRequest authRequest) {
        User user = authRequest.toUser(passwordEncoder);
        validateDuplicateUser(user);

        userMapper.save(user);
        return AuthResponse.of(user);
    }

    /**
     * 이메일 중복 체크
     */
    @Transactional
    public void validateDuplicateUser(User user) {
        userMapper.findByEmail(user.getEmail()).ifPresent(m -> {
            throw new IllegalStateException("이미 가입한 유저입니다.");
        });
    }



    @Transactional
    public TokenDto login(AuthRequest authRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = authRequest.toAuthentication();

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .email(authentication.getName())
                .token(tokenDto.getRefreshToken())
                .build();

        Optional<RefreshToken> oldRefreshToken = refreshTokenMapper.findByEmail(authRequest.getEmail());

        oldRefreshToken.ifPresentOrElse(m -> refreshTokenMapper.update(refreshToken), () -> refreshTokenMapper.save(refreshToken));

        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new IllegalStateException("Refresh Token이 유효하지 않습니다.");
        }

        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        RefreshToken refreshToken = refreshTokenMapper.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        if(!refreshToken.getToken().equals((tokenRequestDto.getRefreshToken()))) {
            throw new IllegalStateException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenMapper.update(newRefreshToken);

        return tokenDto;
    }
}

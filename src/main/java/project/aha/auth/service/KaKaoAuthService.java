package project.aha.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.aha.auth.client.ClientKakao;
import project.aha.auth.dto.AuthRequest;
import project.aha.auth.dto.AuthResponse;
import project.aha.auth.dto.TokenDto;
import project.aha.auth.jwt.TokenProvider;
import project.aha.domain.User;
import project.aha.repository.UserMapper;

@Service
@RequiredArgsConstructor
public class KaKaoAuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final ClientKakao clientKakao;
    private final UserMapper userMapper;
    private final TokenProvider tokenProvider;

    @Transactional
    public TokenDto login(AuthRequest authRequest) {
        User kakaoUser = clientKakao.getUserData(authRequest.getAccessToken());
        String socialId = kakaoUser.getSocialId();
        User user = userMapper.findBySocialId(socialId).get();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(socialId, "");
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        if (user == null) {
            // 회원정보 없음 리턴해주기
            userMapper.save(kakaoUser);
        }

        return tokenDto;

    }
}

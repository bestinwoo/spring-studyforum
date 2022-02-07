package project.aha.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.aha.auth.client.ClientGoogle;
import project.aha.auth.dto.AuthRequest;
import project.aha.auth.dto.AuthResponse;
import project.aha.auth.jwt.AuthToken;
import project.aha.auth.jwt.AuthTokenProvider;
import project.aha.domain.User;
import project.aha.repository.UserMapper;

@Service
@RequiredArgsConstructor
public class GoogleAuthService {
    private final ClientGoogle clientGoogle;
    private final UserMapper userMapper;
    private final AuthTokenProvider authTokenProvider;

    @Transactional
    public AuthResponse login(AuthRequest authRequest) {
        User googleUser = clientGoogle.getUserData(authRequest.getAccessToken());
        String socialId = googleUser.getSocialId();
        User user = userMapper.findBySocialId(socialId).get();
        AuthToken appToken = authTokenProvider.createUserAppToken(socialId);

        if (user == null) {
            userMapper.save(googleUser);
        }

        return AuthResponse.builder()
                .appToken(appToken.getToken())
                .build();
    }
}

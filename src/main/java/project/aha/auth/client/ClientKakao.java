package project.aha.auth.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import project.aha.auth.TokenValidFailedException;
import project.aha.auth.dto.KakaoUserResponse;
import project.aha.domain.MemberProvider;
import project.aha.domain.User;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ClientKakao implements ClientProxy{
    @Override
    public User getUserData(String accessToken) {

        KakaoUserResponse kaKaoUserResponse = WebClient.create().get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new TokenValidFailedException("Social Access Token is unauthorized")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new TokenValidFailedException("Internal Server Error")))
                .bodyToMono(KakaoUserResponse.class)
                .block();

        return User.builder()
                .socialId(String.valueOf(kaKaoUserResponse.getId()))
                .nickname(kaKaoUserResponse.getProperties().getNickname())
                .email(kaKaoUserResponse.getKakaoAccount().getEmail())
                .memberProvider(MemberProvider.KAKAO)
                .roleID(1L)
                .profileImagePath(kaKaoUserResponse.getProperties().getProfileImage())
                .build();

    }
}

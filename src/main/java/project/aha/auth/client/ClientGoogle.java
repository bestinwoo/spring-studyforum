package project.aha.auth.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import project.aha.auth.TokenValidFailedException;
import project.aha.auth.dto.GoogleUserResponse;
import project.aha.domain.MemberProvider;
import project.aha.domain.User;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ClientGoogle implements ClientProxy {
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserData(String accessToken) {
        GoogleUserResponse googleUserResponse = WebClient.create().get()
                .uri("https://www.googleapis.com/oauth2/v2/userinfo/", builder -> builder.queryParam("access_token", accessToken).build())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new TokenValidFailedException("Social Access Token is unauthorized")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new TokenValidFailedException("Internal Server Error")))
                .bodyToMono(GoogleUserResponse.class)
                .block();

        return User.builder()
                .socialId(googleUserResponse.getSub())
                .nickname(googleUserResponse.getName())
                .password(passwordEncoder.encode("1234"))
                .email(googleUserResponse.getEmail())
                .memberProvider(MemberProvider.GOOGLE)
                .roleId(2L)
                .profileImagePath(googleUserResponse.getPicture())
                .registerDate(LocalDateTime.now())
                .build();
    }
}

package project.aha.auth.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import project.aha.auth.TokenValidFailedException;
import project.aha.auth.dto.GoogleUserResponse;
import project.aha.domain.MemberProvider;
import project.aha.domain.User;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ClientGoogle implements ClientProxy {
    @Override
    public User getUserData(String accessToken) {
        GoogleUserResponse googleUserResponse = WebClient.create().get()
                .uri("https://oauth2.googleapis.com/tokeninfo", builder -> builder.queryParam("id_token", accessToken).build())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new TokenValidFailedException("Social Access Token is unauthorized")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new TokenValidFailedException("Internal Server Error")))
                .bodyToMono(GoogleUserResponse.class)
                .block();

        return User.builder()
                .socialId(googleUserResponse.getSub())
                .nickname(googleUserResponse.getName())
                .email(googleUserResponse.getEmail())
                .memberProvider(MemberProvider.GOOGLE)
                .roleID(1L)
                .profileImagePath(googleUserResponse.getPicture())
                .build();
    }
}

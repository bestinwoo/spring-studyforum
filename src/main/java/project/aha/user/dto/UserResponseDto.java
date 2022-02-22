package project.aha.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.aha.user.domain.Role;
import project.aha.user.domain.User;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class UserResponseDto {
    private String email;
    private String nickname;
    private String address;
    private LocalDateTime registerDate;
    private Role role; // TODO: 이거 role_id -> role 가져오는거 해야함
    private String profileImagePath;

    public static UserResponseDto of(User user) {
        return UserResponseDto.builder()
                .email(user.getEmail())
                .address(user.getAddress())
                .nickname(user.getNickname())
                .registerDate(user.getRegisterDate())
                .role(Role.MEMBER)
                .profileImagePath(user.getProfileImagePath())
                .build();
    }
}

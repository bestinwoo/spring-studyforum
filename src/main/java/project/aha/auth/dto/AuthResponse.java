package project.aha.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.aha.user.domain.User;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String email;

    public static AuthResponse of(User user) {
        return new AuthResponse(user.getEmail());
    }

}

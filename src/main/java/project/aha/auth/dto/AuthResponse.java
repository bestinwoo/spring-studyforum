package project.aha.auth.dto;

import lombok.Builder;
import lombok.Data;
import project.aha.domain.User;

@Data
@Builder
public class AuthResponse {
    private String email;

    public static AuthResponse of(User user) {
        return new AuthResponse(user.getEmail());
    }

}

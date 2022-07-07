package project.aha.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Builder
public class TokenDto {
	private String grantType;
	private String accessToken;
	private Long accessTokenExpiresIn;
	private Long refreshTokenExpiresIn;
	private String refreshToken;
	private String loginId;
	private Long userId;
}


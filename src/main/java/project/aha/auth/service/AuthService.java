package project.aha.auth.service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.aha.auth.dto.AuthRequest;
import project.aha.auth.dto.AuthResponse;
import project.aha.auth.dto.TokenDto;
import project.aha.auth.dto.TokenRequestDto;
import project.aha.auth.jwt.SecurityUtil;
import project.aha.auth.jwt.TokenProvider;
import project.aha.user.domain.User;
import project.aha.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final TokenProvider tokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final RedisTemplate<String, Object> redisTemplate;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional(rollbackFor = Exception.class)
	public AuthResponse signup(AuthRequest authRequest) throws IllegalStateException {
		User user = authRequest.toUser(passwordEncoder);
		validateDuplicateUser(user.getLoginId());

		userRepository.save(user);
		return AuthResponse.of(user);
	}

	/**
	 * 이메일 중복 체크
	 */
	@Transactional(readOnly = true)
	public void validateDuplicateUser(String loginId) {
		userRepository.findByLoginId(loginId).ifPresent(m -> {
			throw new IllegalStateException("이미 가입한 유저입니다.");
		});
	}

	@Transactional(rollbackFor = Exception.class)
	public TokenDto login(AuthRequest authRequest) {
		UsernamePasswordAuthenticationToken authenticationToken = authRequest.toAuthentication();

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

		redisTemplate.opsForValue()
			.set("RefreshToken:" + authentication.getName(), tokenDto.getRefreshToken(),
				tokenDto.getRefreshTokenExpiresIn() - new Date().getTime(), TimeUnit.MILLISECONDS);
		tokenDto.setLoginId(authRequest.getId());
		tokenDto.setUserId(Long.parseLong(authentication.getName()));
		return tokenDto;
	}

	@Transactional(rollbackFor = Exception.class)
	public TokenDto reissue(TokenRequestDto tokenRequestDto) {
		if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
			throw new IllegalStateException("Refresh Token이 유효하지 않습니다.");
		}

		Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());
		//Redis에 저장된 Refresh Token 꺼내오기
		String key = "RefreshToken:" + authentication.getName();
		String refreshToken = (String)redisTemplate.opsForValue().get(key);

		if (refreshToken == null || !refreshToken.equals(tokenRequestDto.getRefreshToken())) {
			throw new IllegalStateException("토큰의 유저 정보가 일치하지 않습니다.");
		}

		TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

		redisTemplate.opsForValue().set(key, tokenDto.getRefreshToken());
		return tokenDto;
	}

	@Transactional(rollbackFor = Exception.class)
	public void logout() {
		redisTemplate.delete("RefreshToken:" + SecurityUtil.getCurrentMemberId());
	}
}

package project.aha.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.aha.auth.dto.AuthRequest;
import project.aha.auth.dto.AuthResponse;
import project.aha.auth.dto.TokenDto;
import project.aha.auth.dto.TokenRequestDto;
import project.aha.auth.service.AuthService;
import project.aha.common.BasicResponse;
import project.aha.common.ErrorResponse;
import project.aha.common.Result;
import project.aha.common.ValidationSequence;

@RestController
@RequestMapping("/auth")
@CrossOrigin()
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<BasicResponse> signup(
		@Validated(ValidationSequence.class) @RequestBody AuthRequest authRequest) {
		try {
			AuthResponse response = authService.signup(authRequest);
			return ResponseEntity.ok(new Result<>(response));
		} catch (IllegalStateException e) {
			return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
		}
	}

	@PostMapping("/login")
	public ResponseEntity<TokenDto> login(@RequestBody AuthRequest authRequest) {
		return ResponseEntity.ok(authService.login(authRequest));
	}

	@PostMapping("/reissue")
	public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
		return ResponseEntity.ok(authService.reissue(tokenRequestDto));
	}

	@GetMapping("user/{loginId}")
	public ResponseEntity<BasicResponse> checkDuplicate(@PathVariable String loginId) {
		try {
			authService.validateDuplicateUser(loginId);
			return ResponseEntity.ok(new Result<>("가입 가능한 아이디입니다."));
		} catch (IllegalStateException e) {
			return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
		}
	}

	@PostMapping("/logout")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<BasicResponse> logout() {
		try {
			authService.logout();
			return ResponseEntity.ok(new Result<>("로그아웃이 완료되었습니다."));
		} catch (SecurityException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(e.getMessage(), "401"));
		}
	}
}

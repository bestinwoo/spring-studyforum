package project.aha.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import project.aha.auth.dto.AuthRequest;
import project.aha.auth.dto.AuthResponse;
import project.aha.auth.dto.TokenDto;
import project.aha.auth.dto.TokenRequestDto;
import project.aha.auth.service.AuthService;
import project.aha.common.dto.BasicResponse;
import project.aha.common.dto.ErrorResponse;
import project.aha.common.dto.Result;
import project.aha.common.validation.ValidationSequence;

@Tag(name = "auth", description = "인증 API")
@RestController
@RequestMapping("/auth")
@CrossOrigin()
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@Operation(summary = "회원가입", description = "아이디와 비밀번호로 회원가입합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = AuthResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
		@ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
	})
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

	@Operation(summary = "로그인")
	@PostMapping("/login")
	public ResponseEntity<TokenDto> login(@RequestBody AuthRequest authRequest) {
		return ResponseEntity.ok(authService.login(authRequest));
	}

	@Operation(summary = "AccessToken 재발급", description = "Refresh Token으로 Access Token을 재발급")
	@PostMapping("/reissue")
	public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
		return ResponseEntity.ok(authService.reissue(tokenRequestDto));
	}

	@Operation(summary = "아이디 중복체크", description = "해당 아이디가 이미 가입된 아이디인지 중복 체크")
	@Parameter(name = "loginId", description = "중복 체크할 아이디 값")
	@GetMapping("user/{loginId}")
	public ResponseEntity<BasicResponse> checkDuplicate(@PathVariable String loginId) {
		try {
			authService.validateDuplicateUser(loginId);
			return ResponseEntity.ok(new Result<>("가입 가능한 아이디입니다."));
		} catch (IllegalStateException e) {
			return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
		}
	}

	@Operation(summary = "로그아웃")
	@PostMapping("/logout")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Result.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
		@ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
	})
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

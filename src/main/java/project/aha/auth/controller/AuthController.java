package project.aha.auth.controller;

import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/auth")
@CrossOrigin()
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> signup(@RequestBody AuthRequest authRequest) {
		return ResponseEntity.ok(authService.signup(authRequest));
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
			return ResponseEntity.badRequest().body(new ErrorResponse("중복된 아이디입니다."));
		}
	}
}

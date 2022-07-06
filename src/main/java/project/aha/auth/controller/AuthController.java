package project.aha.auth.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

@RestController
@RequestMapping("/auth")
@CrossOrigin()
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<BasicResponse> signup(@Validated @RequestBody AuthRequest authRequest,
		BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getAllErrors()
				.stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.collect(Collectors.toList());
			return ResponseEntity.badRequest().body(new ErrorResponse(errors, "400"));
		} else {
			try {
				AuthResponse response = authService.signup(authRequest);
				return ResponseEntity.ok(new Result<>(response));
			} catch (IllegalStateException e) {
				return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
			}
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
}

package project.aha.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.aha.auth.dto.AuthRequest;
import project.aha.auth.dto.AuthResponse;
import project.aha.auth.dto.TokenDto;
import project.aha.auth.jwt.TokenProvider;
import project.aha.auth.service.AuthService;
import project.aha.auth.service.GoogleAuthService;
import project.aha.auth.service.KaKaoAuthService;
import project.aha.common.dto.ApiResponse;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final KaKaoAuthService kaKaoAuthService;
    private final GoogleAuthService googleAuthService;
    private final TokenProvider authTokenProvider;
    private final AuthService authService;

    @PostMapping(value = "/kakao")
    public ResponseEntity<TokenDto> kakaoAuthRequest(@RequestBody AuthRequest authRequest) {
        return ApiResponse.success(kaKaoAuthService.login(authRequest));
    }

    @PostMapping(value = "/google")
    public ResponseEntity<TokenDto> googleAuthRequest(@RequestBody AuthRequest authRequest) {
        return ApiResponse.success(googleAuthService.login(authRequest));
    }

//    @GetMapping("/refresh")
//    public ResponseEntity<AuthResponse> refreshToken(HttpServletRequest request) {
//        String appToken = JwtHeaderUtil.getAccessToken(request);
//        AuthToken authToken = authTokenProvider.convertAuthToken(appToken);
//
//        if(!authToken.validate()) {
//            return ApiResponse.forbidden(null);
//        }
//
//        AuthResponse authResponse = authService.updateToken(authToken);
//        if(authResponse == null) {
//            return ApiResponse.forbidden(null);
//        }
//        return ApiResponse.success(authResponse);
//    }
}

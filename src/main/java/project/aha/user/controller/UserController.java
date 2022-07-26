package project.aha.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.aha.common.dto.BasicResponse;
import project.aha.common.dto.Result;
import project.aha.user.dto.UserDto;
import project.aha.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	@GetMapping("/{userId}")
	public ResponseEntity<BasicResponse> getUserInfo(@PathVariable Long userId) {
		UserDto.Response userInfo = userService.getUserInfo(userId);
		return ResponseEntity.ok(new Result<>(userInfo));
	}

	@PatchMapping("/{userId}")
	public ResponseEntity<BasicResponse> modifyUserInfo(@PathVariable Long userId,
		@RequestBody UserDto.ModifyIntroduce modify) {
		userService.modifyIntroduce(userId, modify);
		return ResponseEntity.ok().build();
	}

}

package project.aha.user.controller;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import project.aha.board.dto.PostDto;
import project.aha.common.dto.BasicResponse;
import project.aha.common.dto.ErrorResponse;
import project.aha.common.dto.Result;
import project.aha.reply.dto.ReplyDto;
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

	@PatchMapping("/img/{userId}")
	public ResponseEntity<BasicResponse> modifyProfileImage(@PathVariable Long userId,
		@RequestParam MultipartFile file) {
		try {
			String fileName = userService.modifyProfileImage(userId, file);
			return ResponseEntity.ok(new Result<>(fileName));
		} catch (IOException e) {
			return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/post/{userId}")
	public ResponseEntity<BasicResponse> getWritePosts(@PathVariable Long userId, Pageable pageable) {
		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
			Sort.by("writeDate").descending());
		Page<PostDto.Response> writePosts = userService.getWritePosts(userId, pageRequest);
		return ResponseEntity.ok(new Result<>(writePosts));
	}

	@GetMapping("/reply/{userId}")
	public ResponseEntity<BasicResponse> getWriteReplies(@PathVariable Long userId, Pageable pageable) {
		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
			Sort.by("writeDate").descending());
		Page<ReplyDto.Response> writeReplies = userService.getWriteReplies(userId, pageRequest);
		return ResponseEntity.ok(new Result<>(writeReplies));
	}

}

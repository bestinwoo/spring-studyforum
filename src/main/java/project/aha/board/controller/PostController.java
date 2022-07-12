package project.aha.board.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import project.aha.board.dto.PostDto;
import project.aha.board.service.PostService;
import project.aha.common.BasicResponse;
import project.aha.common.ErrorResponse;
import project.aha.common.Result;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class PostController { // TODO: 글 삭제될 때마다 아무도 참조하지 않는 태그 삭제
	private final PostService postService;

	@PostMapping("/post")
	public ResponseEntity<BasicResponse> writePost(PostDto.Request postDto,
		@RequestParam("file") MultipartFile file) {
		try {
			Long id = postService.writePost(postDto, file);
			return ResponseEntity.status(HttpStatus.CREATED).body(new Result<>(id));
		} catch (IOException e) {
			return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
		}
	}

	// @PatchMapping("/post/{postId}")
	// public ResponseEntity<BasicResponse> modifyPost(@PathVariable Long postId, @RequestBody PostDto postDto) {
	// 	if (!validateUser(postId)) {
	// 		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	// 			.body(new ErrorResponse("작성자만 게시글을 삭제할 수 있습니다.", "401"));
	// 	}
	//
	// 	if (postService.modifyPost(postId, postDto) == 0) {
	// 		return ResponseEntity.notFound().build();
	// 	}
	//
	// 	return ResponseEntity.noContent().build();
	// }
	//
	// @GetMapping("/post/{boardId}")
	// public ResponseEntity<BasicResponse> getPostList(@PathVariable Long boardId) {
	// 	List<PostResponse> body = postService.findPostList(boardId);
	//
	// 	if (body.isEmpty()) {
	// 		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("해당 게시판에 게시글이 없습니다."));
	// 	}
	//
	// 	return ResponseEntity.ok(new Result<>(body));
	// }
	//
	// @GetMapping("/post/{boardId}/{postId}")
	// public ResponseEntity<BasicResponse> getPostDetail(@PathVariable Long boardId, @PathVariable Long postId) {
	// 	if (postService.increaseViews(postId) == 0) {
	// 		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("게시글을 찾을 수 없습니다."));
	// 	}
	// 	return ResponseEntity.ok(new Result<>(postService.postDetail(postId)));
	// }
	//
	// @DeleteMapping("/post/{postId}")
	// public ResponseEntity<BasicResponse> deletePost(@PathVariable Long postId) { //TODO : 관리자는 자기 글 아니어도 삭제할 수 있게 수정 필요
	// 	if (!validateUser(postId)) {
	// 		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	// 			.body(new ErrorResponse("작성자만 게시글을 삭제할 수 있습니다.", "401"));
	// 	}
	//
	// 	if (postService.deletePost(postId) == 0) {
	// 		return ResponseEntity.notFound().build();
	// 	}
	// 	return ResponseEntity.noContent().build();
	// }
	//
	// private boolean validateUser(Long postId) {
	// 	Long currentUserId = SecurityUtil.getCurrentMemberId();
	// 	Long writerId = postService.postDetail(postId).getUserId();
	// 	if (currentUserId != writerId) {
	// 		return false;
	// 	}
	// 	return true;
	// }

}

package project.aha.board.controller;

import static org.springframework.http.MediaType.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import project.aha.board.dto.PostDto;
import project.aha.board.service.PostService;
import project.aha.common.dto.BasicResponse;
import project.aha.common.dto.ErrorResponse;
import project.aha.common.dto.Result;
import project.aha.common.validation.ValidationSequence;

@Tag(name = "post", description = "게시글 API")
@RestController
@RequiredArgsConstructor
@CrossOrigin()
@RequestMapping()
public class PostController {
	private final PostService postService;

	@Operation(summary = "게시글 작성")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "CREATED", content = @Content(schema = @Schema(implementation = Result.class), mediaType = APPLICATION_JSON_VALUE)),
		@ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = APPLICATION_JSON_VALUE))
	})
	@PostMapping("/post")
	public ResponseEntity<BasicResponse> writePost(@Validated(ValidationSequence.class) PostDto.Request postDto,
		@RequestParam("file") MultipartFile file) {
		try {
			Long id = postService.writePost(postDto, file);
			return ResponseEntity.status(HttpStatus.CREATED).body(new Result<>(id));
		} catch (IOException e) {
			return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
		}
	}

	@Operation(summary = "게시글 목록 조회")
	@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = PostDto.Response.class), mediaType = APPLICATION_JSON_VALUE))
	@Parameters({
		@Parameter(name = "boardId", description = "게시판 ID"),
		@Parameter(name = "keyword", description = "검색어"),
		@Parameter(name = "tagName", description = "태그 검색어")
	})
	@GetMapping("/post")
	public ResponseEntity<BasicResponse> getPost(Pageable pageable, @RequestParam Long boardId,
		@RequestParam(required = false, defaultValue = "") String keyword,
		@RequestParam(required = false) List<String> tagName) {
		Page<PostDto.Response> posts;

		if (tagName != null && !tagName.isEmpty()) {
			tagName = tagName.stream().map(tag -> "%" + tag + "%").collect(Collectors.toList());
			posts = postService.getPostByTagName(pageable, boardId, tagName);
		} else {
			posts = postService.getPostsByKeywordAndSort(pageable, boardId, keyword);
		}
		return ResponseEntity.ok(new Result<>(posts, posts.getNumberOfElements()));
	}

	@Operation(summary = "게시글 상세 조회")
	@Parameter(name = "postId", description = "게시글 ID")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = PostDto.Response.class)))
	@GetMapping("/post/{postId}")
	public ResponseEntity<BasicResponse> getPostDetail(@PathVariable Long postId) {
		return ResponseEntity.ok(new Result<>(postService.getPostDetail(postId)));
	}

	@Operation(summary = "게시글 수정")
	@Parameter(name = "postId", description = "게시글 ID")
	@PutMapping("/post/{postId}")
	public ResponseEntity<BasicResponse> modifyPost(@PathVariable Long postId, @RequestBody PostDto.Request request) {
		try {
			postService.modifyPost(postId, request);
			return ResponseEntity.ok().build();
		} catch (AccessDeniedException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(e.getMessage(), "403"));
		}
	}

	@Operation(summary = "게시글 삭제", description = "작성자만 삭제가 가능하다.")
	@Parameter(name = "postId", description = "게시글 ID")
	@DeleteMapping("/post/{postId}")
	public ResponseEntity<BasicResponse> deletePost(@PathVariable Long postId) {
		postService.deletePost(postId);
		return ResponseEntity.ok().build();
	}
}

package project.aha.board.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import project.aha.board.dto.BoardDto;
import project.aha.board.dto.RecentPostResponse;
import project.aha.board.repository.BoardRepository;
import project.aha.board.repository.RecentPostRepository;
import project.aha.common.dto.BasicResponse;
import project.aha.common.dto.ErrorResponse;
import project.aha.common.dto.Result;

@Tag(name = "board", description = "게시판 카테고리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@CrossOrigin
public class BoardController {
	private final BoardRepository boardRepository;
	private final RecentPostRepository recentBoardRepository;

	@Operation(summary = "게시판 목록 조회")
	@ApiResponses({
		@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = BoardDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
		@ApiResponse(responseCode = "404", description = "게시판 목록이 없습니다.")
	})
	@GetMapping()
	public ResponseEntity<BasicResponse> getBoardList() {
		List<BoardDto> boardList = boardRepository.findAll()
			.stream()
			.map(BoardDto::of)
			.collect(Collectors.toList());
		if (boardList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("게시판 목록이 없습니다.", "404"));
		} else {
			return ResponseEntity.ok(new Result<>(boardList));
		}
	}

	@Operation(summary = "최신글 조회", description = "게시판별 최신글 목록 조회")
	@ApiResponses({
		@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = RecentPostResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
		@ApiResponse(responseCode = "404", description = "최신 글이 없습니다.")
	})
	@GetMapping("/recent")
	public ResponseEntity<BasicResponse> getRecentPost() {
		List<RecentPostResponse> post = recentBoardRepository.findAll()
			.stream()
			.map(RecentPostResponse::of)
			.collect(Collectors.toList());
		if (post.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("최신글 목록이 없습니다.", "404"));
		} else {
			return ResponseEntity.ok(new Result<>(post));
		}
	}
}

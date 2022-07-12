package project.aha.board.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.aha.board.dto.BoardDto;
import project.aha.board.repository.BoardRepository;
import project.aha.common.BasicResponse;
import project.aha.common.ErrorResponse;
import project.aha.common.Result;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
	private final BoardRepository boardRepository;

	@GetMapping()
	public ResponseEntity<BasicResponse> getBoardList() {
		List<BoardDto> boardList = boardRepository.findAll().stream().map(BoardDto::of).collect(Collectors.toList());
		if (boardList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("게시판 목록이 없습니다.", "404"));
		} else {
			return ResponseEntity.ok(new Result<>(boardList));
		}
	}
}

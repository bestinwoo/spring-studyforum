package project.aha.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.aha.board.domain.Board;

@Getter
@AllArgsConstructor
public class BoardDto {
	private Long id;
	private String title;

	public static BoardDto of(Board board) {
		return new BoardDto(board.getId(), board.getTitle());
	}
}

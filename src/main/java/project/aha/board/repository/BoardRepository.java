package project.aha.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.aha.board.domain.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
	// @Query(value =
	// 	"select board.id, board.title FROM (SELECT b.id, b.title, p.id as postId, ROW_NUMBER () OVER (PARTITION BY b.id ORDER BY write_date desc) AS date_rank"
	// 		+ " FROM board b inner join post p on b.id = p.board_id order by b.id) as board WHERE TRUE AND date_rank <=5", nativeQuery =
	// @Query(value = "select * from board b, recent_post rp where b.id = rp.board_id", nativeQuery = true)
	// Set<BoardResponse> findBoardAndPostsByFirst4();
}

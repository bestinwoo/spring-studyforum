package project.aha.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.aha.board.domain.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}

package project.aha.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.aha.board.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<project.aha.board.domain.Post, Long> {
	Page<Post> findByBoardIdAndTitleContaining(Long boardId, String keyword, Pageable pageable);
}

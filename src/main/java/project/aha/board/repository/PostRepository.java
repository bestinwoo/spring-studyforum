package project.aha.board.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.aha.board.domain.Post;
import project.aha.board.dto.PostResponse;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	List<PostResponse> findPostByBoardId(Long boardId);

	Page<PostResponse> findByBoardIdAndTitleContaining(Long boardId, String keyword, Pageable pageable);
}

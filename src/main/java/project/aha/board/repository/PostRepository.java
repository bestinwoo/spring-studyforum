package project.aha.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.aha.board.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<project.aha.board.domain.Post, Long> {
	Page<Post> findByBoardIdAndTitleContaining(Long boardId, String keyword, Pageable pageable);

	@Query(value = "select p from Post p where p.board.id = :boardId and p.id in (select pt.post.id from PostTag pt inner join Tag t on pt.tag.id = t.id and t.name like :tagName)")
	Page<Post> findByBoardIdAndTagContaining(@Param(value = "boardId") Long boardId,
		@Param(value = "tagName") String tagName, Pageable pageable);
}

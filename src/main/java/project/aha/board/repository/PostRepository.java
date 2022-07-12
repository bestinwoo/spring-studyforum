package project.aha.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.aha.board.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}

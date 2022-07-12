package project.aha.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.aha.board.domain.PostTag;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Long> {
	Optional<PostTag> findByPostIdAndTagId(Long postId, Long tagId);
}

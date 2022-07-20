package project.aha.board.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.aha.board.domain.PostTag;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Long> {
	Optional<PostTag> findByPostIdAndTagId(Long postId, Long tagId);

	@Query(value = "delete from PostTag t where t.id in :ids")
	@Modifying
	void deleteAllByIdIn(@Param("ids") List<Long> ids);
}

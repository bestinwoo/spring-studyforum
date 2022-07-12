package project.aha.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import project.aha.board.domain.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
	Optional<Tag> findByName(String name);
}

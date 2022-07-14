package project.aha.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.aha.board.domain.RecentPost;

@Repository
public interface RecentPostRepository extends JpaRepository<RecentPost, Long> {
}

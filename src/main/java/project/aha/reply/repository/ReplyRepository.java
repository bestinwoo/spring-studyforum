package project.aha.reply.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import project.aha.reply.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}

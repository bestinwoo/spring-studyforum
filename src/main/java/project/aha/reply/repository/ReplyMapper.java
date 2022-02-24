package project.aha.reply.repository;

import org.apache.ibatis.annotations.Mapper;
import project.aha.reply.domain.Reply;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ReplyMapper {
    Optional<Reply> findById(Long id);
    List<Reply> findByPostId(Long postId);
    Long save(Reply reply);
    Long delete(Long id);
    Long update(Reply reply);
}
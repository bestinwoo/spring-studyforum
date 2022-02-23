package project.aha.board.repository;

import org.apache.ibatis.annotations.Mapper;
import project.aha.board.domain.Post;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostMapper
{
    Long save(Post post);
    Long delete(Long id);
    Long update(Post post);
    Optional<Post> findById(Long id);
    List<Post> findByBoardId(Long boardId);
}

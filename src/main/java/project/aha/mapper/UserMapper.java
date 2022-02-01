package project.aha.mapper;

import org.apache.ibatis.annotations.Mapper;
import project.aha.domain.User;

@Mapper
public interface UserMapper {
    User selectUserById(Long id);

}

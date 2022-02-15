package project.aha.auth.repository;

import org.apache.ibatis.annotations.Mapper;
import project.aha.auth.domain.RefreshToken;

import java.util.Optional;

@Mapper
public interface RefreshTokenMapper {
    Optional<RefreshToken> findByEmail(String email);
    Long save(RefreshToken token);
    Long update(RefreshToken token);
}

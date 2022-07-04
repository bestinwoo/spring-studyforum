package project.aha.auth.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import project.aha.auth.domain.RefreshToken;

@Mapper
public interface RefreshTokenMapper {
	Optional<RefreshToken> findByEmail(String email);

	Long save(RefreshToken token);

	Long update(RefreshToken token);
}

package project.aha.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.aha.auth.jwt.SecurityUtil;
import project.aha.user.domain.User;
import project.aha.user.dto.UserResponseDto;
import project.aha.user.repository.UserMapper;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserMapper userMapper;

	@Transactional(readOnly = true)
	public UserResponseDto getMyInfo() { // TODO: 테스트 필요
		return userMapper.findById(SecurityUtil.getCurrentMemberId())
			.map(UserResponseDto::of)
			.orElseThrow(() -> new IllegalStateException("로그인 유저 정보가 없습니다."));
	}

	public List<User> findUsers() {
		return userMapper.findAll();
	}

	public Optional<User> findOne(Long userId) {
		return userMapper.findById(userId);
	}

	public int delete(Long userId) {
		return userMapper.delete(userId);
	}

}

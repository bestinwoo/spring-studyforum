package project.aha.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.aha.board.repository.PostRepository;
import project.aha.common.ResourceNotFoundException;
import project.aha.reply.repository.ReplyRepository;
import project.aha.user.domain.User;
import project.aha.user.dto.UserDto;
import project.aha.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final ReplyRepository replyRepository;

	@Transactional(readOnly = true)
	public UserDto.Response getUserInfo(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(ResourceNotFoundException::new);
		return UserDto.Response.of(user);
	}

	//@Transactional(rollbackFor = Exception)
}

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
@Transactional(rollbackFor = Exception.class)
public class UserService {
	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final ReplyRepository replyRepository;

	@Transactional(readOnly = true)
	public UserDto.Response getUserInfo(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(ResourceNotFoundException::new);
		return UserDto.Response.of(user);
	}

	public void modifyIntroduce(Long userId, UserDto.ModifyIntroduce modifyIntroduce) {
		User user = userRepository.findById(userId).orElseThrow(ResourceNotFoundException::new);
		// if (!user.getId().equals(SecurityUtil.getCurrentMemberId())) {
		// 	throw new AccessDeniedException("권한이 없습니다.");
		// }
		user.modifyIntroduce(modifyIntroduce.getIntroduce());
	}

	//TODO: 이거 쓴글이랑 댓글도 페이징 필요할것같은디..
	// @Transactional(readOnly = true)
	// public List<PostDto.Response> getWritePosts(Long userId) {
	//
	// }
}

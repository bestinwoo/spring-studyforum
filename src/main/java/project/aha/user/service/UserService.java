package project.aha.user.service;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import project.aha.auth.jwt.SecurityUtil;
import project.aha.board.domain.Post;
import project.aha.board.dto.PostDto;
import project.aha.board.repository.PostRepository;
import project.aha.common.ImageService;
import project.aha.common.ResourceNotFoundException;
import project.aha.reply.domain.Reply;
import project.aha.reply.dto.ReplyDto;
import project.aha.reply.repository.ReplyRepository;
import project.aha.user.domain.User;
import project.aha.user.dto.UserDto;
import project.aha.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserService {
	private final UserRepository userRepository;
	private final ImageService imageService;
	private final PostRepository postRepository;
	private final ReplyRepository replyRepository;

	@Transactional(readOnly = true)
	public UserDto.Response getUserInfo(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(ResourceNotFoundException::new);
		return UserDto.Response.of(user);
	}

	public void modifyIntroduce(Long userId, UserDto.ModifyIntroduce modifyIntroduce) {
		User user = userRepository.findById(userId).orElseThrow(ResourceNotFoundException::new);
		if (!user.getId().equals(SecurityUtil.getCurrentMemberId())) {
			throw new AccessDeniedException("권한이 없습니다.");
		}
		user.modifyIntroduce(modifyIntroduce.getIntroduce());
	}

	public String modifyProfileImage(Long userId, MultipartFile file) throws IOException {
		User user = userRepository.findById(userId).orElseThrow(ResourceNotFoundException::new);
		if (!user.getId().equals(SecurityUtil.getCurrentMemberId())) {
			throw new AccessDeniedException("권한이 없습니다.");
		}
		user.modifyProfileImage(imageService.saveImage(file, userId, "user"));
		return user.getProfileImgPath();
	}

	@Transactional(readOnly = true)
	public Page<PostDto.Response> getWritePosts(Long userId, Pageable pageable) {
		Page<Post> writePosts = postRepository.findByWriterId(userId, pageable);
		if (writePosts.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		return writePosts.map(PostDto.Response::from);
	}

	@Transactional(readOnly = true)
	public Page<ReplyDto.Response> getWriteReplies(Long userId, Pageable pageable) {
		Page<Reply> writeReplies = replyRepository.findByWriterId(userId, pageable);
		return writeReplies.map(ReplyDto.Response::of);
	}

	//회원 탈퇴
	public void quitUser(Long userId) {
		Long currentMemberId = SecurityUtil.getCurrentMemberId();
		User user = userRepository.findById(userId).orElseThrow(ResourceNotFoundException::new);
		if (!user.getId().equals(currentMemberId)) {
			throw new AccessDeniedException("권한이 없습니다.");
		}
		userRepository.delete(user);
	}
}

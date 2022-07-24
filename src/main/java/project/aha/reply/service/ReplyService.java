package project.aha.reply.service;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.aha.auth.jwt.SecurityUtil;
import project.aha.board.domain.Post;
import project.aha.board.repository.PostRepository;
import project.aha.common.ResourceNotFoundException;
import project.aha.reply.domain.Reply;
import project.aha.reply.dto.ReplyDto;
import project.aha.reply.repository.ReplyRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyService {
	private final ReplyRepository replyRepository;
	private final PostRepository postRepository;

	public void writeReply(ReplyDto.Request request) {
		Long userId = SecurityUtil.getCurrentMemberId();
		Post post = postRepository.findById(request.getPostId()).orElseThrow(ResourceNotFoundException::new);
		post.increaseReplyCount();
		Reply reply = request.toReply(userId);

		replyRepository.save(reply);
	}

	public void modifyReply(Long replyId, ReplyDto.Request request) {
		Reply reply = replyRepository.findById(replyId).orElseThrow(ResourceNotFoundException::new);
		Long userId = SecurityUtil.getCurrentMemberId();
		if (!userId.equals(reply.getWriter().getId())) {
			throw new AccessDeniedException("권한이 없습니다.");
		}
		reply.modifyReply(request.getComment());
	}

	public void deleteReply(Long replyId) {
		Reply reply = replyRepository.findById(replyId).orElseThrow(ResourceNotFoundException::new);
		Long userId = SecurityUtil.getCurrentMemberId();
		if (!userId.equals(reply.getWriter().getId())) {
			throw new AccessDeniedException("권한이 없습니다.");
		}
		reply.getPost().decreaseReplyCount();
		replyRepository.delete(reply);
	}

	// public boolean deleteReply(Long postId, Long id) {
	// 	if (replyMapper.delete(id) == 0 || postMapper.decreaseReply(postId) == 0) {
	// 		return false;
	// 	}
	// 	return true;
	// }
	//
	// public Long modifyReply(Reply reply) {
	// 	return replyMapper.update(reply);
	// }
	//
	// public List<Reply> findReplyList(Long postId) {
	// 	return replyMapper.findByPostId(postId);
	// }

}

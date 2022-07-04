package project.aha.reply.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.aha.board.repository.PostMapper;
import project.aha.reply.domain.Reply;
import project.aha.reply.repository.ReplyMapper;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyService {
	private final ReplyMapper replyMapper;
	private final PostMapper postMapper;

	public boolean writeReply(Reply reply) {
		if (replyMapper.save(reply) == 0 || postMapper.increaseReply(reply.getPostId()) == 0) {
			return false;
		}
		return true;
	}

	public boolean deleteReply(Long postId, Long id) {
		if (replyMapper.delete(id) == 0 || postMapper.decreaseReply(postId) == 0) {
			return false;
		}
		return true;
	}

	public Long modifyReply(Reply reply) {
		return replyMapper.update(reply);
	}

	public List<Reply> findReplyList(Long postId) {
		return replyMapper.findByPostId(postId);
	}

}

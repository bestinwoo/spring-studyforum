package project.aha.reply.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.aha.common.BasicResponse;
import project.aha.common.ErrorResponse;
import project.aha.common.Result;
import project.aha.reply.domain.Reply;
import project.aha.reply.service.ReplyService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyController {
	private final ReplyService replyService;

	@PostMapping()
	public ResponseEntity<BasicResponse> writeReply(@RequestBody Reply reply) {
		if (replyService.writeReply(reply) == false) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("댓글 작성 실패"));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(new Result<>("댓글이 작성되었습니다."));
	}

	@PatchMapping()
	public ResponseEntity<BasicResponse> modifyReply(@RequestBody Reply reply) {
		if (replyService.modifyReply(reply) == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("댓글을 찾을 수 없습니다."));
		}
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{postId}")
	public ResponseEntity<BasicResponse> getReplyList(@PathVariable Long postId) {
		List<Reply> replyList = replyService.findReplyList(postId);
		if (replyList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("댓글을 찾을 수 없습니다."));
		}
		return ResponseEntity.ok(new Result<>(replyList));
	}

	@DeleteMapping("/{postId}/{id}")
	public ResponseEntity<BasicResponse> deleteReply(@PathVariable Long postId, @PathVariable Long id) {
		if (replyService.deleteReply(postId, id) == false) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("댓글을 찾을 수 없습니다."));
		}

		return ResponseEntity.noContent().build();
	}

}


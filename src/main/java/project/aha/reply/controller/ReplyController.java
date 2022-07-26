package project.aha.reply.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.aha.common.dto.BasicResponse;
import project.aha.reply.dto.ReplyDto;
import project.aha.reply.service.ReplyService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyController {
	private final ReplyService replyService;

	@PostMapping()
	public ResponseEntity<BasicResponse> writeReply(@RequestBody ReplyDto.Request request) {
		replyService.writeReply(request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PatchMapping("/{replyId}")
	public ResponseEntity<BasicResponse> modifyReply(@PathVariable Long replyId,
		@RequestBody ReplyDto.Request request) {
		replyService.modifyReply(replyId, request);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{replyId}")
	public ResponseEntity<BasicResponse> deleteReply(@PathVariable Long replyId) {
		replyService.deleteReply(replyId);
		return ResponseEntity.ok().build();
	}

	// @DeleteMapping("/{postId}/{id}")
	// public ResponseEntity<BasicResponse> deleteReply(@PathVariable Long postId, @PathVariable Long id) {
	// 	if (replyService.deleteReply(postId, id) == false) {
	// 		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("댓글을 찾을 수 없습니다."));
	// 	}
	//
	// 	return ResponseEntity.noContent().build();
	// }

}


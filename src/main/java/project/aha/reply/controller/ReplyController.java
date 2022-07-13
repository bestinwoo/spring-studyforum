package project.aha.reply.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.aha.reply.service.ReplyService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyController {
	private final ReplyService replyService;
	//
	// @PostMapping()
	// public ResponseEntity<BasicResponse> writeReply(@RequestBody Reply reply) {
	// 	if (replyService.writeReply(reply) == false) {
	// 		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("댓글 작성 실패"));
	// 	}
	// 	return ResponseEntity.status(HttpStatus.CREATED).body(new Result<>("댓글이 작성되었습니다."));
	// }
	//
	// @PatchMapping()
	// public ResponseEntity<BasicResponse> modifyReply(@RequestBody Reply reply) {
	// 	if (replyService.modifyReply(reply) == 0) {
	// 		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("댓글을 찾을 수 없습니다."));
	// 	}
	// 	return ResponseEntity.noContent().build();
	// }
	//
	// @GetMapping("/{postId}")
	// public ResponseEntity<BasicResponse> getReplyList(@PathVariable Long postId) {
	// 	List<Reply> replyList = replyService.findReplyList(postId);
	// 	if (replyList.isEmpty()) {
	// 		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("댓글을 찾을 수 없습니다."));
	// 	}
	// 	return ResponseEntity.ok(new Result<>(replyList));
	// }
	//
	// @DeleteMapping("/{postId}/{id}")
	// public ResponseEntity<BasicResponse> deleteReply(@PathVariable Long postId, @PathVariable Long id) {
	// 	if (replyService.deleteReply(postId, id) == false) {
	// 		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("댓글을 찾을 수 없습니다."));
	// 	}
	//
	// 	return ResponseEntity.noContent().build();
	// }

}


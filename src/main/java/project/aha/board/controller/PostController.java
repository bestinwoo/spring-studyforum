package project.aha.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.aha.auth.jwt.SecurityUtil;
import project.aha.board.dto.PostDto;
import project.aha.board.dto.PostResponse;
import project.aha.board.service.PostService;
import project.aha.common.BasicResponse;

import project.aha.common.ErrorResponse;
import project.aha.common.Result;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @PostMapping()
    public ResponseEntity<BasicResponse> writePost(@RequestBody PostDto postDto) {
        if(postService.writePost(postDto) == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new Result<>("게시글 생성 완료"));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<BasicResponse> modifyPost(@PathVariable Long postId, @RequestBody PostDto postDto) {
        if(!validateUser(postId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("작성자만 게시글을 삭제할 수 있습니다.", "401"));
        }

        if(postService.modifyPost(postId, postDto) == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BasicResponse> getPostList(@PathVariable Long boardId) {
        List<PostResponse> body = postService.findPostList(boardId);

        if(body.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("해당 게시판에 게시글이 없습니다."));
        }

        return ResponseEntity.ok(new Result<>(body));
    }

    @GetMapping("/{boardId}/{postId}")
    public ResponseEntity<BasicResponse> getPostDetail(@PathVariable Long boardId, @PathVariable Long postId) {
        return ResponseEntity.ok(new Result<>(postService.postDetail(postId)));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<BasicResponse> deletePost(@PathVariable Long postId) { //TODO : 관리자는 자기 글 아니어도 삭제할 수 있게 수정 필요
        if(!validateUser(postId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("작성자만 게시글을 삭제할 수 있습니다.", "401"));
        }

        if(postService.deletePost(postId) == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    private boolean validateUser(Long postId) {
        Long currentUserId = SecurityUtil.getCurrentMemberId();
        Long writerId = postService.postDetail(postId).getUserId();
        if(currentUserId != writerId) {
            return false;
        }
        return true;
    }



}

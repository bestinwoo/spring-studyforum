package project.aha.board.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.aha.board.dto.PostDto;
import project.aha.board.dto.PostResponse;
import project.aha.board.service.PostService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @PostMapping()
    public ResponseEntity writePost(@RequestBody PostDto postDto) {
        return ResponseEntity.ok(postService.writePost(postDto));
    }

    @GetMapping()
    public ResponseEntity<Map<String, Object>> getPostList(Long boardId) {
        List<PostResponse> body = postService.findPostList(boardId);

        if(body.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Map<String, Object> result = new HashMap<>();
        result.put("data", body);
        result.put("count", body.size());

        return ResponseEntity.ok().body(result);
    }
//
//    @GetMapping()
//    public Map<String, Object> getPostList(Long boardId) {
//        // List<PostResponse> body = postService.findPostList(boardId);
//        List<PostResponse> body = new ArrayList<PostResponse>();
//        for(int i =0; i < 5; i++) {
//            PostResponse postResponse = PostResponse.builder()
//                    .content("test")
//                    .title("dsa")
//                    .writeDate(LocalDateTime.now())
//                    .writer("inu")
//                    .build();
//            body.add(postResponse);
//        }
//
//        Map<String, Object> result = new HashMap<>();
//        result.put("data", body);
//        result.put("count", body.size());
//        System.out.println(result);
//        return result;
//    }

}

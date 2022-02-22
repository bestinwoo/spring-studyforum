package project.aha.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.aha.auth.jwt.SecurityUtil;
import project.aha.board.dto.PostDto;
import project.aha.board.dto.PostResponse;
import project.aha.board.repository.PostMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostMapper postMapper;

    @Transactional
    public HttpStatus writePost(PostDto postDto) { // TODO: return 어떤걸로 줄지 정해야함
        Long userId = SecurityUtil.getCurrentMemberId();
        postDto.setUserId(userId); // dto에 넣어놓고 여기와서 set해주는거 말고 더 좋은 방법이 있을 것 같음

        postMapper.save(postDto.toPost());
        return HttpStatus.OK;
    }

    @Transactional(readOnly = true)
    public List<PostResponse> findPostList(Long boardId) {
        List<PostResponse> list = postMapper.findByBoardId(boardId).stream().map(PostResponse::of).collect(Collectors.toList());
        return list;
    }



}

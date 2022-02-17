package project.aha.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.aha.board.dto.PostDto;
import project.aha.board.repository.PostMapper;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostMapper postMapper;

    public void writePost(PostDto postDto) {

    }


}

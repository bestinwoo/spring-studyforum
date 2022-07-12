package project.aha.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.aha.auth.jwt.SecurityUtil;
import project.aha.board.domain.Post;
import project.aha.board.dto.PostDto;
import project.aha.board.repository.PostMapper;
import project.aha.board.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostService {
	private final PostMapper postMapper;
	private final PostRepository postRepository;
	private final PostTagService postTagService;

	@Transactional
	public Long writePost(PostDto.Request postDto) {
		Long userId = SecurityUtil.getCurrentMemberId();
		Post savedPost = postRepository.save(postDto.toPost(userId));
		postTagService.saveTags(savedPost, postDto.getTags());
		return savedPost.getId();
	}

	// @Transactional
	// public Long modifyPost(Long postId, PostDto postDto) {
	// 	Long userId = SecurityUtil.getCurrentMemberId();
	// 	postDto.setUserId(userId); // dto에 넣어놓고 여기와서 set해주는거 말고 더 좋은 방법이 있을 것 같음
	// 	postDto.setId(postId);
	// 	return postMapper.update(postDto.toPost());
	// }
	//
	// @Transactional(readOnly = true)
	// public List<PostResponse> findPostList(Long boardId) {
	// 	List<PostResponse> list = postMapper.findByBoardId(boardId)
	// 		.stream()
	// 		.map(PostResponse::of)
	// 		.collect(Collectors.toList());
	// 	return list;
	// }
	//
	// @Transactional(readOnly = true)
	// public PostResponse postDetail(Long postId) {
	// 	return postMapper.findById(postId).map(PostResponse::of).orElseThrow(IllegalStateException::new);
	// }
	//
	// @Transactional
	// public Long deletePost(Long postId) {
	// 	return postMapper.delete(postId);
	// }
	//
	// @Transactional
	// public Long increaseViews(Long id) {
	// 	return postMapper.increaseViews(id);
	// }

}

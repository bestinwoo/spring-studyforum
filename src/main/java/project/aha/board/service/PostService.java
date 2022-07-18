package project.aha.board.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import project.aha.auth.jwt.SecurityUtil;
import project.aha.board.domain.Post;
import project.aha.board.dto.PostDto;
import project.aha.board.repository.PostRepository;
import project.aha.common.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;
	private final PostTagService postTagService;

	@Transactional(rollbackFor = Exception.class)
	public Long writePost(PostDto.Request postDto, MultipartFile file) throws IOException {
		Long userId = SecurityUtil.getCurrentMemberId();
		project.aha.board.domain.Post savedPost = postRepository.save(postDto.toPost(userId));
		postTagService.saveTags(savedPost, postDto.getTags());
		Long postId = savedPost.getId();
		if (file != null) {
			savedPost.setImage_path(saveImage(file, postId));
		}
		return postId;
	}

	// TODO: 이미지 서비스로 빼기, png랑 jpg만 입력받게 하기
	private String saveImage(MultipartFile multipartFile, Long postId) throws IOException {
		String absolutePath = new File("").getAbsolutePath() + "\\";
		String path = "src/main/resources/images/";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		String fileName = "post-" + postId + ".png";
		String imagePath = path + "/" + fileName;

		file = new File(absolutePath + imagePath);
		multipartFile.transferTo(file);
		return fileName;
	}

	@Transactional(readOnly = true)
	public Page<PostDto.Response> getPostsByKeywordAndSort(Pageable pageable, Long boardId, String keyword) {
		Page<Post> posts = postRepository.findByBoardIdAndTitleContaining(boardId,
			keyword, pageable);
		if (posts.isEmpty()) {
			throw new ResourceNotFoundException();
		}

		return posts.map(PostDto.Response::from);
	}

	@Transactional(rollbackFor = Exception.class)
	public PostDto.Response getPostDetail(Long postId) {
		Post post = postRepository.findById(postId).orElseThrow(ResourceNotFoundException::new);
		post.increaseViews();
		PostDto.Response postDto = PostDto.Response.from(post);
		postDto.setContent(post.getContent());
		return postDto;
	}

	// @Transactional(readOnly = true)
	// public Page<PostDto.Response> getPostByTagName(Pageable pageable, Long boardId, String tagName) {
	// 	return postRepository.findByBoardIdAndTagContaining(boardId, tagName, pageable).map(PostDto.Response::from);
	// }

	@Transactional(readOnly = true)
	public Page<PostDto.Response> getPostByTagName(Pageable pageable, Long boardId, List<String> tagName) {
		return postRepository.findByBoardIdAndTagContaining(boardId, tagName, pageable).map(PostDto.Response::from);
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

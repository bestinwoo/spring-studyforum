package project.aha.board.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import project.aha.auth.jwt.SecurityUtil;
import project.aha.board.domain.Post;
import project.aha.board.domain.PostTag;
import project.aha.board.domain.Tag;
import project.aha.board.dto.PostDto;
import project.aha.board.repository.PostRepository;
import project.aha.common.ImageService;
import project.aha.common.ResourceNotFoundException;
import project.aha.reply.dto.ReplyDto;

@Service
@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;
	private final PostTagService postTagService;
	private final ImageService imageService;

	@Transactional(rollbackFor = Exception.class)
	public Long writePost(PostDto.Request postDto, MultipartFile file) throws IOException {
		Long userId = SecurityUtil.getCurrentMemberId();
		project.aha.board.domain.Post savedPost = postRepository.save(postDto.toPost(userId));
		postTagService.saveTags(savedPost, postDto.getTags());
		Long postId = savedPost.getId();
		if (file != null) {
			savedPost.setImage_path(imageService.saveImage(file, postId, "post"));
		}
		return postId;
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
		postDto.setReplies(post.getReplies().stream().map(ReplyDto.Response::of).collect(Collectors.toList()));
		return postDto;
	}

	@Transactional(readOnly = true)
	public Page<PostDto.Response> getPostByTagName(Pageable pageable, Long boardId, List<String> tagName) {
		return postRepository.findByBoardIdAndTagContaining(boardId, tagName, pageable).map(PostDto.Response::from);
	}

	@Transactional(rollbackFor = Exception.class)
	public void modifyPost(Long postId, PostDto.Request request) {
		Long userId = SecurityUtil.getCurrentMemberId();
		Post post = postRepository.findById(postId).orElseThrow(ResourceNotFoundException::new);
		if (post.getWriter() == null || !post.getWriter().getId().equals(userId)) {
			throw new AccessDeniedException("?????? ????????? ????????????.");
		}
		//?????? ????????? ?????? ??? ????????? ????????? ?????? ?????? ????????? (???????????? ??? ?????????)
		List<PostTag> deleteForPostTags = post.getTags()
			.stream()
			.filter(p -> !request.getTags().contains(p.getTag().getName()))
			.collect(Collectors.toList());

		List<Tag> deleteForTags = deleteForPostTags.stream().map(PostTag::getTag).collect(Collectors.toList());

		if (!deleteForPostTags.isEmpty()) {
			postTagService.deletePostTags(deleteForPostTags.stream().map(PostTag::getId).collect(Collectors.toList()));
			postTagService.deleteOrphanTags(deleteForTags.stream().map(Tag::getId).collect(Collectors.toList()));
		}
		//???????????? ????????? ?????? ????????? ?????? ?????????????????? ????????????
		//?????? ?????? ???????????? ?????? ???????????? ???????????? ???????????? ?????? ????????? ???????????? ???????????????
		List<String> names = deleteForTags.stream().map(Tag::getName).collect(Collectors.toList());
		request.setTags(request.getTags().stream().filter(rq -> !names.contains(rq)).collect(Collectors.toSet()));
		post.modifyPost(request);
		postTagService.saveTags(post, request.getTags());
	}

	@Transactional(rollbackFor = Exception.class)
	public void deletePost(Long postId) {
		Long userId = SecurityUtil.getCurrentMemberId();
		Post post = postRepository.findById(postId).orElseThrow(ResourceNotFoundException::new);
		if (post.getWriter() == null || !post.getWriter().getId().equals(userId)) {
			throw new AccessDeniedException("?????? ????????? ????????????.");
		}

		List<Long> deleteTagIds = post.getTags().stream().map(PostTag::getTag).map(Tag::getId).collect(
			Collectors.toList());

		postRepository.delete(post);
		//?????? ?????? ??????
		if (!post.getTags().isEmpty()) {
			postTagService.deleteOrphanTags(deleteTagIds);
		}
	}
}

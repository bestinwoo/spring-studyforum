package project.aha.board.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.aha.board.domain.Post;
import project.aha.board.domain.PostTag;
import project.aha.board.domain.Tag;
import project.aha.board.repository.PostTagRepository;

@Service
@RequiredArgsConstructor
public class PostTagService {
	private final PostTagRepository postTagRepository;
	private final TagService tagService;

	@Transactional
	public void saveTags(Post post, Set<String> tags) {
		Set<PostTag> postTag = mapToPostTags(post, tags);
		post.setTags(postTag);
	}

	private Set<PostTag> mapToPostTags(Post post, Set<String> tags) {
		Set<PostTag> postTags = tags.stream()
			.map(tagName -> {
				Tag tag = tagService.findOrCreateTag(tagName);
				return findOrCreatePostTag(post, tag);
			}).collect(Collectors.toSet());
		return postTags;
	}

	private PostTag findOrCreatePostTag(Post post, Tag tag) {
		PostTag postTag = postTagRepository.findByPostIdAndTagId(post.getId(), tag.getId())
			.orElse(PostTag.builder()
				.post(Post.builder()
					.id(post.getId())
					.build())
				.tag(Tag.builder()
					.id(tag.getId())
					.build())
				.build());
		return postTagRepository.save(postTag);
	}
}

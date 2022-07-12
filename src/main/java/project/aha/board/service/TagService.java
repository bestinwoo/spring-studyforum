package project.aha.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.aha.board.domain.Tag;
import project.aha.board.repository.TagRepository;

@Service
@RequiredArgsConstructor
public class TagService {
	private final TagRepository tagRepository;

	@Transactional
	public Tag findOrCreateTag(String tagName) {
		Tag tag = tagRepository.findByName(tagName)
			.orElse(Tag.builder()
				.name(tagName)
				.build());
		return tagRepository.save(tag);
	}
}

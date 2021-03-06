package project.aha.board.domain;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.aha.board.dto.PostDto;
import project.aha.reply.domain.Reply;
import project.aha.user.domain.User;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Builder
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String content;
	private String title;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User writer;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private Board board;
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<PostTag> tags;
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Reply> replies;
	private Long views;
	private Long replyCount;
	private LocalDateTime writeDate;
	private String imagePath;

	public void setTags(Set<PostTag> tags) {
		if (this.tags == null) {
			this.tags = tags;
		} else {
			this.tags.addAll(tags);
		}
		for (PostTag tag : tags) {
			tag.setPost(this);
		}
	}

	public void increaseViews() {
		this.views += 1;
	}

	public void increaseReplyCount() {
		this.replyCount += 1;
	}

	public void decreaseReplyCount() {
		this.replyCount -= 1;
	}

	public void modifyPost(PostDto.Request request) {
		this.content = request.getContent();
		this.title = request.getTitle();
	}

	public void setImage_path(String imagePath) {
		this.imagePath = imagePath;
	}
}

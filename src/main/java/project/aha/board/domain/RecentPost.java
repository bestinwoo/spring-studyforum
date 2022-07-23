package project.aha.board.domain;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Immutable;

import lombok.Getter;
import project.aha.user.domain.User;

@Entity
@Immutable
@Getter
public class RecentPost {
	@Id
	private Long postId;
	private String postTitle;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User writer;
	@ManyToOne
	@JoinColumn(name = "board_id")
	private Board board;
	private LocalDateTime writeDate;
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<PostTag> tags;
	private Long views;
	private Long replyCount;
	private String imagePath;
}

package project.aha.board.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
	private Long views;
	private Long replyCount;
	private String imagePath;
}

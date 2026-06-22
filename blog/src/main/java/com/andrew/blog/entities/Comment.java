package com.andrew.blog.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comments")
@Getter @Setter
@NoArgsConstructor
public class Comment extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Comment parent;

	@Column(nullable = false)
	private String body;

	@Column(nullable = false)
	private Boolean isDeleted = false;

	public Comment(User user, Post post, Comment parent, String body) {
		this.user = user;
		this.post = post;
		this.parent = parent;
		this.body = body;
		this.isDeleted = false;
	}
}
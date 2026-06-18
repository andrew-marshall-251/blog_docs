package com.andrew.blog.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User author;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "thread_id")
	private Thread thread;

	@Column(nullable = false)
	private String title;

	@Lob
	private String body;

	@Column(nullable = false)
	private String slug;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime lastUpdatedAt;

	@CreationTimestamp
	private LocalDateTime publishedAt;

	public Post(
			User author,
			Thread thread,
			String title,
			String body,
			String slug,
			Status status) {
		this.author = author;
		this.thread = thread;
		this.title = title;
		this.body = body;
		this.slug = slug;
		this.status = status;
	}
}
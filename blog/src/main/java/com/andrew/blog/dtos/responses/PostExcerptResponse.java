package com.andrew.blog.dtos.responses;

import com.andrew.blog.entities.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class PostExcerptResponse {
	private Long authorId;
	private String authorName;
	private Long postId;
	private String postTitle;
	private String postExcerpt;
	private String slug;
	private Long threadId;
	private String threadName;
	private LocalDateTime createdAt;
	private LocalDateTime lastUpdatedAt;
	private LocalDateTime publishedAt;
	private Status status;
}

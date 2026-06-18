package com.andrew.blog.dtos.responses;

import com.andrew.blog.entities.Status;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
public class CreatePostResponse {
	private Long authorId;
	private String authorName;
	private Long postId;
	private String postTitle;
	private String postBody;
	private String slug;
	private Long threadId;
	private String threadName;
	private LocalDateTime createdAt;
	private LocalDateTime lastUpdatedAt;
	private LocalDateTime publishedAt;
	private Status status;
}

package com.andrew.blog.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UpdateCommentResponse {
	private Long authorId;
	private String authorName;
	private Long postId;
	private String postTitle;
	private Long commentId;
	private String commentBody;
	private Long parentCommentId;
	private LocalDateTime createdAt;
	private LocalDateTime lastUpdatedAt;
}

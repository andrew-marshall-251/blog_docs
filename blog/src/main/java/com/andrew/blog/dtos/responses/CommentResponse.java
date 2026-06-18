package com.andrew.blog.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
public class CommentResponse {
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

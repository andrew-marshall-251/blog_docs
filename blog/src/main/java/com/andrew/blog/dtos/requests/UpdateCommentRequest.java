package com.andrew.blog.dtos.requests;

import lombok.Data;

@Data
public class UpdateCommentRequest {
	private String commentBody;
	private Long parentId;
}

package com.andrew.blog.dtos.requests;

import lombok.Data;

@Data
public class CreateCommentRequest {
	private String commentBody;
	private Long parentId;
}

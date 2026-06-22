package com.andrew.blog.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateCommentRequest {
	private String commentBody;
	private Long parentId;
}

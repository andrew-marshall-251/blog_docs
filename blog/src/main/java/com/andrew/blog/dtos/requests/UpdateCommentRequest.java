package com.andrew.blog.dtos.requests;

import lombok.Setter;
import lombok.Getter;

@Getter @Setter
public class UpdateCommentRequest {
	private String commentBody;
}

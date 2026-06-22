package com.andrew.blog.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdatePostRequest {
	private Long threadId;
	private String postBody;
	private String postTitle;
}

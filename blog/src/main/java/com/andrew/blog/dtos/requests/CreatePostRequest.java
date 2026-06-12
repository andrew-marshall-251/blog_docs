package com.andrew.blog.dtos.requests;

import lombok.Setter;
import lombok.Getter;

@Getter @Setter
public class CreatePostRequest {
	private String title;
	private String body;
	private Long threadId;
}

package com.andrew.blog.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreatePostRequest {
	private String title;
	private String body;
	private Long threadId;
}

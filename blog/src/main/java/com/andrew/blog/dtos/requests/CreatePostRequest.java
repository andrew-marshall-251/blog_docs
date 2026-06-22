package com.andrew.blog.dtos.requests;

import lombok.Data;

@Data
public class CreatePostRequest {
	private String title;
	private String body;
	private Long threadId;
}

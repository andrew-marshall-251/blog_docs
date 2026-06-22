package com.andrew.blog.dtos.requests;

import lombok.Data;

@Data
public class UpdatePostRequest {
	private Long threadId;
	private String postBody;
	private String postTitle;
}

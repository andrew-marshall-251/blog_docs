package com.andrew.blog.dtos.requests;

import lombok.Data;

@Data
public class UpdateSelfRequest {
	private String username;
	private String email;
	private Long mascotId;
	private String bio;
}

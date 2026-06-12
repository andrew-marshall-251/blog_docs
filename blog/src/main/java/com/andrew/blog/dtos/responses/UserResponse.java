package com.andrew.blog.dtos.responses;

import lombok.Setter;
import lombok.Getter;

@Getter @Setter
public class UserResponse {
	private Long userId;
	private String username;
	private Long mascotId;
	private String bio;
}

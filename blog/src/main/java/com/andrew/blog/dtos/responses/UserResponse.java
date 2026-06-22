package com.andrew.blog.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class UserResponse {
	private Long userId;
	private String username;
	private Long mascotId;
	private String bio;
}

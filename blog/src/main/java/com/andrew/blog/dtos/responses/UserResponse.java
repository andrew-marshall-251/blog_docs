package com.andrew.blog.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
	private Long userId;
	private String username;
	private Long mascotId;
	private String bio;
}

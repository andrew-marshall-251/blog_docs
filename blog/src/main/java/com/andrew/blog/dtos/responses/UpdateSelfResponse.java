package com.andrew.blog.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateSelfResponse {
	private Long userId;
	private String username;
	private String email;
	private Long mascotId;
	private String bio;
}

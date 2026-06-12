package com.andrew.blog.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateSelfResponse {
	private Long userId;
	private String username;
	private String email;
	private Long mascotId;
	private String bio;
}

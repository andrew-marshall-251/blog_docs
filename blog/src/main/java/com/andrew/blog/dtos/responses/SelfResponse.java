package com.andrew.blog.dtos.responses;

import lombok.Setter;
import lombok.Getter;

@Getter @Setter
public class SelfResponse {
	private Long userId;
	private String username;
	private String email;
	private Long mascotId;
	private String bio;
}

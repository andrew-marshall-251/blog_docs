package com.andrew.blog.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Getter @Setter
@AllArgsConstructor
public class SelfResponse {
	private Long userId;
	private String username;
	private String email;
	private Long mascotId;
	private String bio;
}

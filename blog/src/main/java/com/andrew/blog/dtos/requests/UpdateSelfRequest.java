package com.andrew.blog.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateSelfRequest {
	private String username;
	private String email;
	private Long mascotId;
	private String bio;
}

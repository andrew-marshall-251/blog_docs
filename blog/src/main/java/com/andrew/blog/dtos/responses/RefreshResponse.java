package com.andrew.blog.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class RefreshResponse {
	private String accessToken;
	private String tokenType;
	private Long expiresIn;
}

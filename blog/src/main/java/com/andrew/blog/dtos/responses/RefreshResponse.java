package com.andrew.blog.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RefreshResponse {
	private String accessToken;
	private String tokenType;
	private Long expiresIn;
}

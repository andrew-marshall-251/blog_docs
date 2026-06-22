package com.andrew.blog.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
	private String token;
	private String tokenType;
	private Long expiresIn;
}

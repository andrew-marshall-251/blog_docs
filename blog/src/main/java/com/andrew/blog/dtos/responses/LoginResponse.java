package com.andrew.blog.dtos.responses;

import com.andrew.blog.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LoginResponse {
	private Long id;
	private TokenResponse accessToken;
	private TokenResponse refreshToken;
	private String username;
	private List<Role> roles;
}

package com.andrew.blog.dtos.responses;

import com.andrew.blog.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class LoginResponse {
	private Long id;
	private TokenResponse accessToken;
	private TokenResponse refreshToken;
	private String username;
	private List<Role> roles;
}

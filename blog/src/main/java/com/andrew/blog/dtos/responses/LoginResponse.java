package com.andrew.blog.dtos.responses;

import com.andrew.blog.entities.Role;
import lombok.Setter;
import lombok.Getter;

import java.util.List;

@Getter @Setter
public class LoginResponse {
	private Long id;
	private String accessToken;
	private String tokenType;
	private Long expiresIn;
	private String username;
	private List<Role> roles;
}

package com.andrew.blog.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequest {
	private String usernameOrEmail;
	private String password;
}

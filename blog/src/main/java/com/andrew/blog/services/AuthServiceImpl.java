package com.andrew.blog.services;

import com.andrew.blog.dtos.requests.LoginRequest;
import com.andrew.blog.dtos.responses.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
	@Override
	public LoginResponse login(LoginRequest request) {
		return null;
	}
}

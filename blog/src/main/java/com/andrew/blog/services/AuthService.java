package com.andrew.blog.services;

import com.andrew.blog.dtos.requests.LoginRequest;
import com.andrew.blog.dtos.requests.RefreshRequest;
import com.andrew.blog.dtos.responses.LoginResponse;
import com.andrew.blog.dtos.responses.RefreshResponse;
import jakarta.validation.Valid;

public interface AuthService {
	LoginResponse	login(LoginRequest request);
	RefreshResponse refresh(@Valid RefreshRequest request);
	void revokeRefresh(String username);
}

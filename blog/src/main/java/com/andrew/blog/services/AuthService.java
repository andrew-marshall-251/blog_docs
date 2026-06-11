package com.andrew.blog.services;

import com.andrew.blog.dtos.requests.LoginRequest;
import com.andrew.blog.dtos.responses.LoginResponse;

public interface AuthService {
	LoginResponse	login(LoginRequest request);
}

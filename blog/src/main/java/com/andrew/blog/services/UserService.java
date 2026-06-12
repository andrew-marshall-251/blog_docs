package com.andrew.blog.services;

import com.andrew.blog.dtos.requests.CreateUserRequest;
import com.andrew.blog.dtos.responses.CreateUserResponse;
import com.andrew.blog.dtos.responses.PostListResponse;
import com.andrew.blog.dtos.responses.UserResponse;

public interface UserService {
	CreateUserResponse createUser(CreateUserRequest request);
	UserResponse getUser(Long id);
	void deleteUser(Long id);
}

package com.andrew.blog.services;

import com.andrew.blog.dtos.requests.CreateUserRequest;
import com.andrew.blog.dtos.responses.CreateUserResponse;
import com.andrew.blog.dtos.responses.UserResponse;
import com.andrew.blog.entities.User;

public interface UserService {
	CreateUserResponse createUser(CreateUserRequest request);
	UserResponse getUser(Long id);
	void deleteUser(Long id);
	User getUserFromRequest(CreateUserRequest request, boolean isAdmin);
	User getUserFromRequest(CreateUserRequest request);
	void addFirstAdmin();
	void validateUsername(String username);
	void validateEmail(String email);
}

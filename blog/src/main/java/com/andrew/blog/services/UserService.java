package com.andrew.blog.services;

import com.andrew.blog.dtos.requests.CreatedUserRequest;
import com.andrew.blog.dtos.responses.CreatedUserResponse;
import com.andrew.blog.dtos.responses.PostListResponse;
import com.andrew.blog.dtos.responses.UserResponse;

public interface UserService {
	CreatedUserResponse createUser(CreatedUserRequest request);
	UserResponse getUser(Long id);
	Void deleteUser(Long id);
	PostListResponse getUserPosts(Long id);
}

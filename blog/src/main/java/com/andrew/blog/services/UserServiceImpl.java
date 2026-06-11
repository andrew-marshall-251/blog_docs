package com.andrew.blog.services;

import com.andrew.blog.dtos.requests.CreatedUserRequest;
import com.andrew.blog.dtos.responses.CreatedUserResponse;
import com.andrew.blog.dtos.responses.PostListResponse;
import com.andrew.blog.dtos.responses.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Override
	public CreatedUserResponse createUser(CreatedUserRequest request) {
		return null;
	}

	@Override
	public UserResponse getUser(Long id) {
		return null;
	}

	@Override
	public Void deleteUser(Long id) {
		return null;
	}

	@Override
	public PostListResponse getUserPosts(Long id) {
		return null;
	}
}

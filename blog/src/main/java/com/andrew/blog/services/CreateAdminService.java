package com.andrew.blog.services;

import com.andrew.blog.dtos.requests.CreateUserRequest;
import com.andrew.blog.dtos.responses.CreateUserResponse;

public interface CreateAdminService {
	CreateUserResponse createAdmin(CreateUserRequest request);
}

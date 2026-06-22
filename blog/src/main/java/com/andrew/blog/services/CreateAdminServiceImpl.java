package com.andrew.blog.services;

import com.andrew.blog.dtos.requests.CreateUserRequest;
import com.andrew.blog.dtos.responses.CreateUserResponse;
import com.andrew.blog.entities.User;
import com.andrew.blog.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class CreateAdminServiceImpl implements CreateAdminService {
	private UserService userService;
	private UserRepository userRepository;

	public CreateAdminServiceImpl(
			UserRepository userRepository,
			UserService userService) {
		this.userRepository = userRepository;
		this.userService = userService;
	}

	@Override
	@Transactional
	public CreateUserResponse createAdmin(
			@Valid @RequestBody CreateUserRequest request) {
		User newUser = userService.getUserFromRequest(request, true);
		userRepository.save(newUser);
		// create response
		CreateUserResponse response = new CreateUserResponse(
				newUser.getId(),
				newUser.getUsername(),
				newUser.getEmail(),
				newUser.getMascot().getId(),
				newUser.getBio());
		return response;
	}
}

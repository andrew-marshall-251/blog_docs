package com.andrew.blog.controllers;

import com.andrew.blog.dtos.requests.CreateUserRequest;
import com.andrew.blog.dtos.responses.CreateUserResponse;
import com.andrew.blog.services.CreateAdminService;
import com.andrew.blog.services.CreateAdminServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class CreateAdminController {
	private CreateAdminService createAdminService;

	public CreateAdminController(CreateAdminService createAdminService, CreateAdminServiceImpl createAdminServiceImpl) {
		this.createAdminService = createAdminService;
	}
	@PostMapping("/admins")
	public ResponseEntity<CreateUserResponse> createAdmin(
			@Valid @RequestBody CreateUserRequest request) {
		System.out.println("hello");
		CreateUserResponse response = createAdminService.createAdmin(request);
		URI location = URI.create("/users/" + response.getUserId());
		return ResponseEntity
				.created(location)
				.body(response);
	}
}

package com.andrew.blog.controllers;

import com.andrew.blog.dtos.requests.CreateUserRequest;
import com.andrew.blog.dtos.responses.CreateUserResponse;
import com.andrew.blog.dtos.responses.UserResponse;
import com.andrew.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/users")
	public ResponseEntity<CreateUserResponse> createUser(
			@Valid @RequestBody CreateUserRequest request) {
		System.out.println("hello");
		CreateUserResponse response = userService.createUser(request);
		URI location = URI.create("/api/v1/users/" + response.getUserId());
		return ResponseEntity.created(location).body(response);
	}

	@GetMapping("/users/{user_id}")
	public ResponseEntity<UserResponse> getUser(
			@PathVariable("user_id") Long id) {
		UserResponse response = userService.getUser(id);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/users/{user_id}")
	public ResponseEntity<Void> deleteUser(
			@PathVariable("user_id") Long id) {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
}

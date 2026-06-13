package com.andrew.blog.controllers;

import com.andrew.blog.dtos.errors.IsNotAdminException;
import com.andrew.blog.dtos.requests.CreateUserRequest;
import com.andrew.blog.dtos.responses.CreateUserResponse;
import com.andrew.blog.dtos.responses.UserResponse;
import com.andrew.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	private void adminVerification(Authentication auth) {
		boolean isAdmin = auth
				.getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
		if (!isAdmin) {
			throw new IsNotAdminException(auth.getName());
		}
	}

	@PostMapping("")
	public ResponseEntity<CreateUserResponse> createUser(
			@Valid @RequestBody CreateUserRequest request) {
		System.out.println("hello");
		CreateUserResponse response = userService.createUser(request);
		URI location = URI.create("/users/" + response.getUserId());
		return ResponseEntity
				.created(location)
				.body(response);
	}

	@GetMapping("/{user_id}")
	public ResponseEntity<UserResponse> getUser(
			@PathVariable("user_id") Long id) {
		UserResponse response = userService.getUser(id);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(response);
	}

	@DeleteMapping("/{user_id}")
	public ResponseEntity<Void> deleteUser(
			Authentication auth,
			@PathVariable("user_id") Long id) {
		adminVerification(auth);
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
}

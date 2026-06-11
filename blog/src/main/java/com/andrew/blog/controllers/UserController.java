package com.andrew.blog.controllers;

import com.andrew.blog.dtos.requests.CreatedUserRequest;
import com.andrew.blog.dtos.responses.CreatedUserResponse;
import com.andrew.blog.dtos.responses.PostListResponse;
import com.andrew.blog.dtos.responses.UserResponse;
import com.andrew.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	@PostMapping("/")
	public ResponseEntity<CreatedUserResponse> createUser(
			@Valid @RequestBody CreatedUserRequest request) {
		CreatedUserResponse response = userService.createUser(request);
		return ResponseEntity.body(response);
	}

	@GetMapping("/{user_id}")
	public ResponseEntity<UserResponse> getUser(@PathVariable("user_id") Long id) {
		UserResponse response = userService.getUser(id);
		return ResponseEntity.body(response);
	}

	@DeleteMapping("/{user_id}")
	public ResponseEntity<Void> deleteUser(@PathVariable("user_id") Long id) {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{user_id}/posts")
	public ResponseEntity<PostListResponse> getUserPosts(@PathVariable("user_id") Long id) {
		PostListResponse response = userService.getUserPosts(id);
		return ResponseEntity.body(response);
	}
}

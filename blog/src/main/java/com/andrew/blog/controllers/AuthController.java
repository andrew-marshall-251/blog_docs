package com.andrew.blog.controllers;

import com.andrew.blog.dtos.requests.LoginRequest;
import com.andrew.blog.dtos.requests.RefreshRequest;
import com.andrew.blog.dtos.responses.LoginResponse;
import com.andrew.blog.dtos.responses.RefreshResponse;
import com.andrew.blog.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/auth/login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
		LoginResponse response = authService.login(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/auth/refresh")
	public ResponseEntity<RefreshResponse> refresh(@Valid @RequestBody RefreshRequest request) {
		RefreshResponse response = authService.refresh(request);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/auth/refresh")
	public ResponseEntity<Void> revokeRefresh(Authentication auth) {
		String username = auth.getName();
		authService.revokeRefresh(username);
		return ResponseEntity.noContent().build();
	}
}

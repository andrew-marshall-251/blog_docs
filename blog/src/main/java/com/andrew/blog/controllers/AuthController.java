package com.andrew.blog.controllers;

import com.andrew.blog.dtos.requests.LoginRequest;
import com.andrew.blog.dtos.responses.LoginResponse;
import com.andrew.blog.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
		try {
			LoginResponse response = authService.login(request);
			return ResponseEntity.body(response);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			throw new RuntimeException();
		}
	}
}

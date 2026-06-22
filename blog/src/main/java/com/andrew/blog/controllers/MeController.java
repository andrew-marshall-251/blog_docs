package com.andrew.blog.controllers;

import com.andrew.blog.dtos.requests.UpdateSelfPasswordRequest;
import com.andrew.blog.dtos.requests.UpdateSelfRequest;
import com.andrew.blog.dtos.responses.PostListResponse;
import com.andrew.blog.dtos.responses.SelfResponse;
import com.andrew.blog.dtos.responses.UpdateSelfResponse;
import com.andrew.blog.services.MeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class MeController {
	private final MeService meService;

	public MeController(MeService meService) {
		this.meService = meService;
	}

	@GetMapping("/users/me")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<SelfResponse> getSelf(Authentication auth) {
		String username = auth.getName();
		SelfResponse response = meService.getSelf(username);
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/users/me")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<UpdateSelfResponse> updateSelf(
			@Valid @RequestBody UpdateSelfRequest request,
			Authentication auth) {
		String username = auth.getName();
		UpdateSelfResponse response = meService.updateSelf(request, username);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/users/me")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Void> deleteSelf(Authentication auth) {
		String username = auth.getName();
		meService.deleteSelf(username);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/users/me/password")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<UpdateSelfResponse> updateSelfPassword(
			@Valid @RequestBody UpdateSelfPasswordRequest request,
			Authentication auth) {
		String username = auth.getName();
		UpdateSelfResponse response = meService.updateSelfPassword(request, username);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/users/me/posts")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<PostListResponse> getSelfPosts(Authentication auth) {
		String username = auth.getName();
		PostListResponse response = meService.getSelfPosts(username);
		return ResponseEntity.ok(response);
	}
}

package com.andrew.blog.controllers;

import com.andrew.blog.dtos.responses.PostListResponse;
import com.andrew.blog.dtos.responses.SelfResponse;
import com.andrew.blog.services.MeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MeController {
	private final MeService meService;

	public MeController(MeService meService) {
		this.meService = meService;
	}

	@GetMapping("/users/me")
	public ResponseEntity<SelfResponse> getSelf(Authentication auth) {
		SelfResponse response = meService.getSelf(auth);

	}

	@PatchMapping("/users/me")
	public ResponseEntity<SelfResponse> updateSelf(Authentication auth) {
		SelfResponse response = meService.updateSelf(auth);

	}

	@DeleteMapping("/users/me")
	public ResponseEntity<Void> deleteSelf(Authentication auth) {
		meService.deleteSelf(auth);
	}

	@PatchMapping("/users/me/password")
	public ResponseEntity<SelfResponse> updateSelfPassword(Authentication auth) {
		SelfResponse response = meService.updateSelfPassword(auth);

	}

	@GetMapping("/users/me/posts")
	public ResponseEntity<PostListResponse> getSelfPosts(Authentication auth) {
		PostListResponse response = meService.getSelfPosts(auth);

	}
}

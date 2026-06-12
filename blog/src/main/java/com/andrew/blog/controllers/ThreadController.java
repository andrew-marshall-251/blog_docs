package com.andrew.blog.controllers;

import com.andrew.blog.dtos.errors.IsNotAdminException;
import com.andrew.blog.dtos.requests.CreateThreadRequest;
import com.andrew.blog.dtos.requests.CreateUserRequest;
import com.andrew.blog.dtos.requests.UpdateThreadRequest;
import com.andrew.blog.dtos.responses.*;
import com.andrew.blog.services.ThreadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ThreadController {
	private final ThreadService threadService;

	public ThreadController(ThreadService threadService) {
		this.threadService = threadService;
	}

	private void adminVerification(Authentication auth) {
		boolean isAdmin = auth
				.getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
		if (!isAdmin) {
			throw new IsNotAdminException(auth.getName());
		}
	}

	@GetMapping("/threads")
	public ResponseEntity<ThreadListResponse> getAllThreads() {
		ThreadListResponse response = threadService.getAllThreads();
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(response);
	}

	@PostMapping("/threads")
	public ResponseEntity<CreateThreadResponse> createThread(
			@Valid @RequestBody CreateThreadRequest request,
			Authentication auth) {
		adminVerification(auth);
		CreateThreadResponse response = threadService.createThread(request);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(response);
	}

	@GetMapping("/threads/{thread_id}")
	public ResponseEntity<ThreadResponse> getThread(
			@PathVariable("thread_id") Long id) {
		ThreadResponse response = threadService.getThread(id);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(response);
	}

	@PatchMapping("/threads/{thread_id}")
	public ResponseEntity<UpdateThreadResponse> updateThread(
			@Valid @RequestBody UpdateThreadRequest request,
			Authentication auth,
			@PathVariable("thread_id") Long id) {
		adminVerification(auth);
		UpdateThreadResponse response = threadService.updateThread(request, id);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(response);
	}

	@DeleteMapping("/threads/{thread_id}")
	public ResponseEntity<Void> deleteThread(
			@PathVariable("thread_id") Long id,
			Authentication auth) {
		adminVerification(auth);
		threadService.deleteThread(id);
		return ResponseEntity.noContent().build();
	}
}

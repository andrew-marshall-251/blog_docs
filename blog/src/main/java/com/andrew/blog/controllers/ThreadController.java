package com.andrew.blog.controllers;

import com.andrew.blog.dtos.requests.CreatedUserRequest;
import com.andrew.blog.dtos.requests.UpdateThreadRequest;
import com.andrew.blog.dtos.responses.*;
import com.andrew.blog.services.ThreadService;
import jakarta.validation.Valid;
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

	@GetMapping("/threads")
	public ResponseEntity<ThreadListResponse> getAllThreads() {
		ThreadListResponse response = threadService.getAllThreads();
		return ResponseEntity.body(response);
	}

	@PostMapping("/threads")
	public ResponseEntity<CreateThreadResponse> createThread(
			@Valid @RequestBody CreatedUserRequest request,
			Authentication auth) {
		CreateThreadResponse response = threadService.createThread(request, auth);
		return ResponseEntity.body(response);
	}

	@GetMapping("/threads/{thread_id}")
	public ResponseEntity<ThreadResponse> getThread(
			@PathVariable("thread_id") Long id) {
		ThreadResponse response = threadService.getThread();
		return ResponseEntity.body(response);
	}

	@PatchMapping("/threads/{thread_id}")
	public ResponseEntity<UpdateThreadResponse> updateThread(
			@Valid @RequestBody UpdateThreadRequest request,
			Authentication auth,
			@PathVariable("thread_id") Long id) {
		UpdateThreadResponse response = threadService.updateThread(request, auth, id);
		return ResponseEntity.body(response);
	}

	@DeleteMapping("/threads/{thread_id}")
	public ResponseEntity<Void> deleteThread(
			@PathVariable("thread_id") Long id) {
		threadService.deleteThread(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/threads/{thread_id}/posts")
	public ResponseEntity<PostListResponse> getThreadPosts(
			@PathVariable("thread_id") Long id) {
		PostListResponse response = threadService.getThreadPosts(id);
		return ResponseEntity.body(response);
	}
}

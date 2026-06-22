package com.andrew.blog.controllers;

import com.andrew.blog.dtos.requests.CreateThreadRequest;
import com.andrew.blog.dtos.requests.UpdateThreadRequest;
import com.andrew.blog.dtos.responses.*;
import com.andrew.blog.services.ThreadService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
public class ThreadController {
	private final ThreadService threadService;

	public ThreadController(ThreadService threadService) {
		this.threadService = threadService;
	}

	@GetMapping("/threads")
	public ResponseEntity<ThreadListResponse> getAllThreads() {
		ThreadListResponse response = threadService.getAllThreads();
		return ResponseEntity.ok(response);
	}

	@PostMapping("/threads")
	public ResponseEntity<CreateThreadResponse> createThread(
			@Valid @RequestBody CreateThreadRequest request) {
		CreateThreadResponse response = threadService.createThread(request);
		URI location = URI.create("/api/v1/threads/" + response.getId());
		return ResponseEntity.created(location).body(response);
	}

	@GetMapping("/threads/{thread_id}")
	public ResponseEntity<ThreadResponse> getThread(
			@PathVariable("thread_id") Long id) {
		ThreadResponse response = threadService.getThread(id);
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/threads/{thread_id}")
	public ResponseEntity<UpdateThreadResponse> updateThread(
			@Valid @RequestBody UpdateThreadRequest request,
			@PathVariable("thread_id") Long id) {
		UpdateThreadResponse response = threadService.updateThread(request, id);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/threads/{thread_id}")
	public ResponseEntity<Void> deleteThread(
			@PathVariable("thread_id") Long id,
			Authentication auth) {
		threadService.deleteThread(id);
		return ResponseEntity.noContent().build();
	}
}

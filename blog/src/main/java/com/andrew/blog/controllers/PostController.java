package com.andrew.blog.controllers;

import com.andrew.blog.dtos.requests.CreatedUserRequest;
import com.andrew.blog.dtos.requests.UpdatePostRequest;
import com.andrew.blog.dtos.responses.PostListResponse;
import com.andrew.blog.dtos.responses.CreatePostResponse;
import com.andrew.blog.dtos.responses.PostResponse;
import com.andrew.blog.dtos.responses.UpdatePostResponse;
import com.andrew.blog.services.PostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PostController {
	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@GetMapping("/posts")
	public ResponseEntity<PostListResponse> getAllPosts() {
		PostListResponse response = postService.getAllPosts();
		return ResponseEntity.body(response);
	}

	@PostMapping("/posts")
	public ResponseEntity<CreatePostResponse> createPost(
			@Valid @RequestBody CreatedUserRequest request,
			Authentication auth) {
		CreatePostResponse response = postService.createPost(request, auth);
		return ResponseEntity.body(response);
	}

	@GetMapping("/posts/{post_id}")
	public ResponseEntity<PostResponse> getPost(@PathVariable("post_id") Long id) {
		PostResponse response = postService.getPost(id);
		return ResponseEntity.body(response);
	}

	@PatchMapping("/posts/{post_id}")
	public ResponseEntity<UpdatePostResponse> createPost(
			@Valid @RequestBody UpdatePostRequest request,
			Authentication auth,
			@PathVariable("post_id") Long id) {
		UpdatePostRequest response = postService.updatePost(request, auth, id);
		return ResponseEntity.body(response);
	}

	@DeleteMapping("/posts/{post_id}")
	public ResponseEntity<Void> deletePost(@PathVariable("post_id") Long id) {
		postService.deletePost(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/users/{user_id}/posts")
	public ResponseEntity<PostListResponse> getUserPosts(@PathVariable("post_id") Long id) {
		PostListResponse response = postService.getUserPosts(id);
		return ResponseEntity.body(response);
	}

	@GetMapping("/threads/{thread_id}/posts")
	public ResponseEntity<PostListResponse> getThreadPosts(@PathVariable("post_id") Long id) {
		PostListResponse response = postService.getThreadPosts(id);
		return ResponseEntity.body(response);
	}
}

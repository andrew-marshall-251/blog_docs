package com.andrew.blog.controllers;

import com.andrew.blog.dtos.requests.CreatePostRequest;
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

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
public class PostController {
	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@GetMapping("/posts")
	public ResponseEntity<PostListResponse> getAllPosts() {
		System.out.println("hi");
		PostListResponse response = postService.getAllPosts();
		return ResponseEntity.ok(response);
	}

	@PostMapping("/posts")
	public ResponseEntity<CreatePostResponse> createPost(
			@Valid @RequestBody CreatePostRequest request,
			Authentication auth) {
		String username = auth.getName();
		CreatePostResponse response = postService.createPost(request, username);
		URI location = URI.create("/api/v1/posts/" + response.getPostId());
		return ResponseEntity.created(location).body(response);
	}

	@GetMapping("/posts/{post_id}")
	public ResponseEntity<PostResponse> getPost(@PathVariable("post_id") Long id) {
		PostResponse response = postService.getPost(id);
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/posts/{post_id}")
	public ResponseEntity<UpdatePostResponse> updatePost(
			@Valid @RequestBody UpdatePostRequest request,
			Authentication auth,
			@PathVariable("post_id") Long id) {
		String authorName = auth.getName();
		UpdatePostResponse response = postService.updatePost(request, authorName, id);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/posts/{post_id}")
	public ResponseEntity<Void> deletePost(
			@PathVariable("post_id") Long id,
			Authentication auth) {
		String username = auth.getName();
		postService.deletePost(username, id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/users/{user_id}/posts")
	public ResponseEntity<PostListResponse> getUserPosts(@PathVariable("user_id") Long id) {
		PostListResponse response = postService.getUserPosts(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/threads/{thread_id}/posts")
	public ResponseEntity<PostListResponse> getThreadPosts(@PathVariable("thread_id") Long id) {
		PostListResponse response = postService.getThreadPosts(id);
		return ResponseEntity.ok(response);
	}
}

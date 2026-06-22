package com.andrew.blog.controllers;

import com.andrew.blog.dtos.requests.CreateCommentRequest;
import com.andrew.blog.dtos.requests.UpdateCommentRequest;
import com.andrew.blog.dtos.responses.CommentListResponse;
import com.andrew.blog.dtos.responses.CommentResponse;
import com.andrew.blog.dtos.responses.CreateCommentResponse;
import com.andrew.blog.dtos.responses.UpdateCommentResponse;
import com.andrew.blog.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
public class CommentController {
	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@GetMapping("/posts/{post_id}/comments")
	public ResponseEntity<CommentListResponse> getPostComments(
			@PathVariable("post_id") Long id) {
		CommentListResponse response = commentService.getPostComments(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/comments/{comment_id}")
	public ResponseEntity<CommentResponse> getComment(
			@PathVariable("comment_id") Long id) {
		CommentResponse response = commentService.getComment(id);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/posts/{post_id}/comments")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<CreateCommentResponse> createComment(
			@Valid @RequestBody CreateCommentRequest request,
			Authentication auth,
			@PathVariable("post_id") Long id) {
		String username = auth.getName();
		CreateCommentResponse response = commentService.createComment(request, username, id);
		URI location = URI.create("/api/v1/comments/" + response.getCommentId());
		return ResponseEntity.created(location).body(response);
	}

	@PatchMapping("/comments/{comment_id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<UpdateCommentResponse> updateComment(
			@Valid @RequestBody UpdateCommentRequest request,
			Authentication auth,
			@PathVariable("comment_id") Long id) {
		String username = auth.getName();
		UpdateCommentResponse response = commentService.updateComment(request, username, id);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/comments/{comment_id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Void> deleteComment(
			@PathVariable("comment_id") Long id,
			Authentication auth) {
		String username = auth.getName();
		commentService.deleteComment(id, username);
		return ResponseEntity.noContent().build();
	}
}

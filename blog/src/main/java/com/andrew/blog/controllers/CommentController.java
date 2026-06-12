package com.andrew.blog.controllers;

import com.andrew.blog.dtos.requests.CreateCommentRequest;
import com.andrew.blog.dtos.requests.UpdateCommentRequest;
import com.andrew.blog.dtos.responses.CommentListResponse;
import com.andrew.blog.dtos.responses.CreateCommentResponse;
import com.andrew.blog.dtos.responses.UpdateCommentResponse;
import com.andrew.blog.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@GetMapping("/posts/{post_id}/comments")
	public ResponseEntity<CommentListResponse> getPostComments(
			@PathVariable("post_id") Long id) {
		CommentListResponse response = commentService.getPostComments(id);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(response);
	}

	@PostMapping("/posts/{post_id}/comments")
	public ResponseEntity<CreateCommentResponse> createComment(
			@Valid @RequestBody CreateCommentRequest request,
			Authentication auth,
			@PathVariable("post_id") Long id) {
		String username = auth.getName();
		CreateCommentResponse response = commentService.createComment(request, username, id);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(response);
	}

	@PatchMapping("/comments/{comment_id}")
	public ResponseEntity<UpdateCommentResponse> updateComment(
			@Valid @RequestBody UpdateCommentRequest request,
			Authentication auth,
			@PathVariable("comment_id") Long id) {
		String username = auth.getName();
		UpdateCommentResponse response = commentService.updateComment(request, username, id);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(response);
	}

	@DeleteMapping("/comments/{comment_id}")
	public ResponseEntity<Void> deleteComment(
			@PathVariable("comment_id") Long id,
			Authentication auth) {
		String username = auth.getName();
		commentService.deleteComment(id, username);
		return ResponseEntity.noContent().build();
	}
}

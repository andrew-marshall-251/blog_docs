package com.andrew.blog.controllers;

import com.andrew.blog.dtos.requests.CreateCommentRequest;
import com.andrew.blog.dtos.requests.UpdateCommentRequest;
import com.andrew.blog.dtos.responses.CommentListResponse;
import com.andrew.blog.dtos.responses.CreateCommentResponse;
import com.andrew.blog.dtos.responses.UpdateCommentResponse;
import com.andrew.blog.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

public class CommentController {
	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@GetMapping("/posts/{post_id}/comments")
	public ResponseEntity<CommentListResponse> getPostsComments(
			@PathVariable("post_id") Long id) {
		CommentListResponse response = commentService.getPostsComments(id);
		return ResponseEntity.body(response);
	}

	@PostMapping("/posts/{post_id}/comments")
	public ResponseEntity<CreateCommentResponse> createComment(
			@Valid @RequestBody CreateCommentRequest request,
			Authentication auth,
			@PathVariable("post_id") Long id) {
		CreateCommentResponse response = commentService.createComment(request, auth, id);
		return ResponseEntity.body(response);
	}

	@PatchMapping("/comments/{comment_id}")
	public ResponseEntity<UpdateCommentResponse> updateComment(
			@Valid @RequestBody UpdateCommentRequest request,
			Authentication auth,
			@PathVariable("comment_id") Long id) {
		UpdateCommentResponse response = commentService.updateComment(request, auth, id);
		return ResponseEntity.body(response);
	}

	@DeleteMapping("/comments/{comment_id}")
	public ResponseEntity<Void> deleteComment(@PathVariable("comment_id") Long id) {
		commentService.deleteComment(id);
		return ResponseEntity.noContent().build();
	}
}

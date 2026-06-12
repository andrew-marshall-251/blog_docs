package com.andrew.blog.services;

import com.andrew.blog.dtos.requests.CreateCommentRequest;
import com.andrew.blog.dtos.requests.UpdateCommentRequest;
import com.andrew.blog.dtos.responses.CommentListResponse;
import com.andrew.blog.dtos.responses.CreateCommentResponse;
import com.andrew.blog.dtos.responses.UpdateCommentResponse;
import jakarta.validation.Valid;

public interface CommentService {
	void deleteComment(Long id, String auth);
	UpdateCommentResponse updateComment(UpdateCommentRequest request, String username, Long id);
	CreateCommentResponse createComment(CreateCommentRequest request, String username, Long id);
	CommentListResponse getPostComments(Long id);
}

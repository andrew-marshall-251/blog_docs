package com.andrew.blog.services;

import com.andrew.blog.dtos.requests.CreateCommentRequest;
import com.andrew.blog.dtos.requests.UpdateCommentRequest;
import com.andrew.blog.dtos.responses.CommentListResponse;
import com.andrew.blog.dtos.responses.CreateCommentResponse;
import com.andrew.blog.dtos.responses.UpdateCommentResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

public interface CommentService {
	void deleteComment(Long id);
	UpdateCommentResponse updateComment(@Valid UpdateCommentRequest request, Authentication auth, Long id);
	CreateCommentResponse createComment(@Valid CreateCommentRequest request, Authentication auth, Long id);
	CommentListResponse getPostsComments(Long id);
}

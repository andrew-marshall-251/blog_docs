package com.andrew.blog.services;

import com.andrew.blog.dtos.requests.CreateCommentRequest;
import com.andrew.blog.dtos.requests.UpdateCommentRequest;
import com.andrew.blog.dtos.responses.CommentListResponse;
import com.andrew.blog.dtos.responses.CreateCommentResponse;
import com.andrew.blog.dtos.responses.UpdateCommentResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
	@Override
	public void deleteComment(Long id) {

	}

	@Override
	public UpdateCommentResponse updateComment(UpdateCommentRequest request, Authentication auth, Long id) {
		return null;
	}

	@Override
	public CreateCommentResponse createComment(CreateCommentRequest request, Authentication auth, Long id) {
		return null;
	}

	@Override
	public CommentListResponse getPostsComments(Long id) {
		return null;
	}
}

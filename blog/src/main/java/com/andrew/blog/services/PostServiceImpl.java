package com.andrew.blog.services;

import com.andrew.blog.dtos.requests.CreatedUserRequest;
import com.andrew.blog.dtos.requests.UpdatePostRequest;
import com.andrew.blog.dtos.responses.CreatePostResponse;
import com.andrew.blog.dtos.responses.PostListResponse;
import com.andrew.blog.dtos.responses.PostResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {
	@Override
	public PostListResponse getAllPosts() {
		return null;
	}

	@Override
	public CreatePostResponse createPost(CreatedUserRequest request, Authentication auth) {
		return null;
	}

	@Override
	public PostResponse getPost(Long id) {
		return null;
	}

	@Override
	public UpdatePostRequest updatePost(UpdatePostRequest request, Authentication auth, Long id) {
		return null;
	}

	@Override
	public void deletePost(Long id) {

	}

	@Override
	public PostListResponse getUserPosts(Long id) {
		return null;
	}

	@Override
	public PostListResponse getThreadPosts(Long id) {
		return null;
	}
}

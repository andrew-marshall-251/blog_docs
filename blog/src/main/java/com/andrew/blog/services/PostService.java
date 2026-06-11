package com.andrew.blog.services;

import com.andrew.blog.dtos.requests.CreatedUserRequest;
import com.andrew.blog.dtos.requests.UpdatePostRequest;
import com.andrew.blog.dtos.responses.CreatePostResponse;
import com.andrew.blog.dtos.responses.PostListResponse;
import com.andrew.blog.dtos.responses.PostResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

public interface PostService {
	PostListResponse getAllPosts();
	CreatePostResponse createPost(@Valid CreatedUserRequest request, Authentication auth);
	PostResponse getPost(Long id);
	UpdatePostRequest updatePost(@Valid UpdatePostRequest request, Authentication auth, Long id);
	void deletePost(Long id);
	PostListResponse getUserPosts(Long id);
	PostListResponse getThreadPosts(Long id);
}

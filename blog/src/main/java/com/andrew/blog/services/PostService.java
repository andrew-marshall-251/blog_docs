package com.andrew.blog.services;

import com.andrew.blog.dtos.requests.CreatePostRequest;
import com.andrew.blog.dtos.requests.UpdatePostRequest;
import com.andrew.blog.dtos.responses.CreatePostResponse;
import com.andrew.blog.dtos.responses.PostListResponse;
import com.andrew.blog.dtos.responses.PostResponse;
import com.andrew.blog.dtos.responses.UpdatePostResponse;
import com.andrew.blog.entities.Post;

import java.util.List;

public interface PostService {
	PostListResponse getAllPosts();
	CreatePostResponse createPost(CreatePostRequest request, String username);
	PostResponse getPost(Long id);
	UpdatePostResponse updatePost(UpdatePostRequest request, String authorName, Long id);
	PostListResponse getPostListResponseFromPosts(List<Post> posts);
	void deletePost(String username, Long id);
	PostListResponse getUserPosts(Long id);
	PostListResponse getThreadPosts(Long id);
}

package com.andrew.blog.services;

import com.andrew.blog.dtos.requests.CreatedUserRequest;
import com.andrew.blog.dtos.responses.CreateThreadResponse;
import com.andrew.blog.dtos.responses.PostListResponse;
import com.andrew.blog.dtos.responses.ThreadListResponse;
import com.andrew.blog.dtos.responses.ThreadResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

public interface ThreadService {
	PostListResponse getThreadPosts(Long id);
	void deleteThread(Long id);
	ThreadResponse getThread();
	CreateThreadResponse createThread(@Valid CreatedUserRequest request, Authentication auth);
	ThreadListResponse getAllThreads();
}

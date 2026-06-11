package com.andrew.blog.services;

import com.andrew.blog.dtos.requests.CreatedUserRequest;
import com.andrew.blog.dtos.responses.CreateThreadResponse;
import com.andrew.blog.dtos.responses.PostListResponse;
import com.andrew.blog.dtos.responses.ThreadListResponse;
import com.andrew.blog.dtos.responses.ThreadResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ThreadServiceImpl implements ThreadService {
	@Override
	public PostListResponse getThreadPosts(Long id) {
		return null;
	}

	@Override
	public void deleteThread(Long id) {

	}

	@Override
	public ThreadResponse getThread() {
		return null;
	}

	@Override
	public CreateThreadResponse createThread(CreatedUserRequest request, Authentication auth) {
		return null;
	}

	@Override
	public ThreadListResponse getAllThreads() {
		return null;
	}
}

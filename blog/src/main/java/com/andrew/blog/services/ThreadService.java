package com.andrew.blog.services;

import com.andrew.blog.dtos.requests.CreateThreadRequest;
import com.andrew.blog.dtos.requests.CreateUserRequest;
import com.andrew.blog.dtos.requests.UpdateThreadRequest;
import com.andrew.blog.dtos.responses.*;
import jakarta.validation.Valid;

public interface ThreadService {
	void deleteThread(Long id);
	ThreadResponse getThread(Long id);
	CreateThreadResponse createThread(CreateThreadRequest request);
	ThreadListResponse getAllThreads();
	UpdateThreadResponse updateThread(UpdateThreadRequest request, Long id);
}

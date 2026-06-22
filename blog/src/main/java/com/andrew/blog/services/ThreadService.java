package com.andrew.blog.services;

import com.andrew.blog.dtos.requests.CreateThreadRequest;
import com.andrew.blog.dtos.requests.UpdateThreadRequest;
import com.andrew.blog.dtos.responses.CreateThreadResponse;
import com.andrew.blog.dtos.responses.ThreadListResponse;
import com.andrew.blog.dtos.responses.ThreadResponse;
import com.andrew.blog.dtos.responses.UpdateThreadResponse;

public interface ThreadService {
	void deleteThread(Long id);
	ThreadResponse getThread(Long id);
	CreateThreadResponse createThread(CreateThreadRequest request);
	ThreadListResponse getAllThreads();
	UpdateThreadResponse updateThread(UpdateThreadRequest request, Long id);
}

package com.andrew.blog.services;

import com.andrew.blog.dtos.errors.ThreadNotFoundByIdException;
import com.andrew.blog.dtos.requests.CreateThreadRequest;
import com.andrew.blog.dtos.requests.UpdateThreadRequest;
import com.andrew.blog.dtos.responses.*;
import com.andrew.blog.entities.Thread;
import com.andrew.blog.repositories.PostRepository;
import com.andrew.blog.repositories.ThreadRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ThreadServiceImpl implements ThreadService {
	private final ThreadRepository threadRepository;
	private final PostRepository postRepository;

	public ThreadServiceImpl(ThreadRepository threadRepository, PostRepository postRepository) {
		this.threadRepository = threadRepository;
		this.postRepository = postRepository;
	}

	@Override
	public void deleteThread(Long id) {
		// errors
		Thread thread = threadRepository.findById(id)
				.orElseThrow(() -> new ThreadNotFoundByIdException(id));
		// delete
		threadRepository.delete(thread);
	}

	@Override
	public ThreadResponse getThread(Long id) {
		// errors
		Thread thread = threadRepository.findById(id)
				.orElseThrow(() -> new ThreadNotFoundByIdException(id));
		// create response
		ThreadResponse response = new ThreadResponse();
		response.setId(thread.getId());
		response.setName(thread.getName());
		Long postCount = postRepository.countByThreadId(id);
		response.setPostCount(postCount);
		return response;
	}

	@Override
	public CreateThreadResponse createThread(CreateThreadRequest request) {
		// create new thread
		Thread newThread = new Thread();
		newThread.setName(request.getThreadName());
		threadRepository.save(newThread);
		// create response
		CreateThreadResponse response = new CreateThreadResponse();
		response.setId(newThread.getId());
		response.setName(newThread.getName());
		Long postCount = postRepository.countByThreadId(newThread.getId());
		response.setPostCount(postCount);
		return response;
	}

	@Override
	public ThreadListResponse getAllThreads() {
		List<Thread> threads = threadRepository.findAll();
		List<ThreadResponse> threadResponses = new ArrayList<>();
		for (Thread thread: threads) {
			ThreadResponse threadResponse = new ThreadResponse();
			threadResponse.setId(thread.getId());
			threadResponse.setName(thread.getName());
			Long postCount = postRepository.countByThreadId(thread.getId());
			threadResponse.setPostCount(postCount);
			threadResponses.add(threadResponse);
		}
		ThreadListResponse response = new ThreadListResponse();
		response.setThreads(threadResponses);
		return response;
	}

	@Override
	public UpdateThreadResponse updateThread(UpdateThreadRequest request, Long id) {
		// errors
		Thread thread = threadRepository.findById(id)
				.orElseThrow(() -> new ThreadNotFoundByIdException(id));
		// update
		if (request.getThreadName() != null) {
			thread.setName(request.getThreadName());
		}
		threadRepository.save(thread);
		// create response
		UpdateThreadResponse response = new UpdateThreadResponse();
		response.setId(thread.getId());
		response.setName(thread.getName());
		Long postCount = postRepository.countByThreadId(thread.getId());
		response.setPostCount(postCount);
		return response;
	}
}

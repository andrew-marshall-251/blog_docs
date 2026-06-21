package com.andrew.blog.services;

import com.andrew.blog.dtos.errors.ThreadNameAlreadyTakenException;
import com.andrew.blog.dtos.errors.ThreadNotFoundByIdException;
import com.andrew.blog.dtos.requests.CreateThreadRequest;
import com.andrew.blog.dtos.requests.UpdateThreadRequest;
import com.andrew.blog.dtos.responses.*;
import com.andrew.blog.entities.Thread;
import com.andrew.blog.repositories.PostRepository;
import com.andrew.blog.repositories.ThreadRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThreadServiceImpl implements ThreadService {
	private final ThreadRepository threadRepository;
	private final PostRepository postRepository;

	public ThreadServiceImpl(ThreadRepository threadRepository, PostRepository postRepository) {
		this.threadRepository = threadRepository;
		this.postRepository = postRepository;
	}

	private Long getPostCountFromThread(Thread thread) {
		return postRepository.countByThreadId(thread.getId());
	}

	@Override
	public void deleteThread(Long id) {
		// errors
		Thread thread = threadRepository.findById(id)
				.orElseThrow(() -> new ThreadNotFoundByIdException(id));
		// delete
		thread.setName("[deletedThread]");
		threadRepository.save(thread);
	}

	@Override
	public ThreadResponse getThread(Long id) {
		// errors
		Thread thread = threadRepository.findById(id)
				.orElseThrow(() -> new ThreadNotFoundByIdException(id));
		if (thread.getName().equals("[deletedThread]")) {
			throw new ThreadNotFoundByIdException(id);
		}
		// create response
		ThreadResponse response = new ThreadResponse(
				thread.getId(),
				thread.getName(),
				getPostCountFromThread(thread));
		return response;
	}

	@Override
	public CreateThreadResponse createThread(CreateThreadRequest request) {
		// create new thread
		if (threadRepository.existsByName(request.getThreadName())
			|| request.getThreadName().equals("[deletedThread]")) {
			throw new ThreadNameAlreadyTakenException(request.getThreadName());
		}
		Thread newThread = new Thread();
		newThread.setName(request.getThreadName());
		threadRepository.save(newThread);
		// create response
		CreateThreadResponse response = new CreateThreadResponse(
				newThread.getId(),
				newThread.getName(),
				getPostCountFromThread(newThread));
		return response;
	}

	@Override
	public ThreadListResponse getAllThreads() {
		List<Thread> threads = threadRepository.findAll();
		threads = threads.stream()
				.filter(thread -> !thread.getName().equals("[deletedThread]"))
				.collect(Collectors.toList());
		List<ThreadResponse> threadResponses = threads.stream()
				.map(thread-> new ThreadResponse(
						thread.getId(),
						thread.getName(),
						getPostCountFromThread(thread)))
				.collect(Collectors.toList());
		ThreadListResponse response = new ThreadListResponse(threadResponses);
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
		UpdateThreadResponse response = new UpdateThreadResponse(
				thread.getId(),
				thread.getName(),
				getPostCountFromThread(thread));
		return response;
	}
}

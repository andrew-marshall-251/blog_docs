package com.andrew.blog.dtos.errors;

public class ThreadNotFoundByIdException extends RuntimeException {
	public ThreadNotFoundByIdException(Long id) {
		super("Thread #" + id + " not found");
	}
}

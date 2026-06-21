package com.andrew.blog.dtos.errors;

public class ThreadNameAlreadyTakenException extends RuntimeException {
	public ThreadNameAlreadyTakenException(String threadName) {
		super("Thread name: " + threadName + " is already taken.");
	}
}

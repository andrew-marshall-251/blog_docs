package com.andrew.blog.dtos.errors;

public class RefreshDoesNotExistException extends RuntimeException {
	public RefreshDoesNotExistException(String message) {
		super(message);
	}
}

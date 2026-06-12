package com.andrew.blog.dtos.errors;

public class UserNotFoundByIdException extends RuntimeException {
	public UserNotFoundByIdException(Long id) {
		super("User #" + id + " not found");
	}
}

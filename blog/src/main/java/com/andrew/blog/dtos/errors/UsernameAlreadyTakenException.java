package com.andrew.blog.dtos.errors;

public class UsernameAlreadyTakenException extends RuntimeException {
	public UsernameAlreadyTakenException(String username) {
		super("Username already taken: " + username);
	}
}

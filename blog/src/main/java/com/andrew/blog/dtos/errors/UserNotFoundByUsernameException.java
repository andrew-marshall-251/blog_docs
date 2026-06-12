package com.andrew.blog.dtos.errors;

public class UserNotFoundByUsernameException extends RuntimeException {
	public UserNotFoundByUsernameException(String username) {
		super("User of username " + username + " not found");
	}
}

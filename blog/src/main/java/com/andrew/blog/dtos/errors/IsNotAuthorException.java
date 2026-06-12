package com.andrew.blog.dtos.errors;

public class IsNotAuthorException extends RuntimeException {
	public IsNotAuthorException(String username) {
		super("User " + username + " is not the author.");
	}
}

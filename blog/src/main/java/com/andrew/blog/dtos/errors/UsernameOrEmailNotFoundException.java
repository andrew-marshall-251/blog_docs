package com.andrew.blog.dtos.errors;

public class UsernameOrEmailNotFoundException extends RuntimeException {
	public UsernameOrEmailNotFoundException(String usernameOrEmail) {
		super("User with username/email: " + usernameOrEmail + " not found");
	}
}

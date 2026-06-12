package com.andrew.blog.dtos.errors;

public class IsNotAdminException extends RuntimeException {
	public IsNotAdminException(String username) {
		super(username + " is not an admin.");
	}
}

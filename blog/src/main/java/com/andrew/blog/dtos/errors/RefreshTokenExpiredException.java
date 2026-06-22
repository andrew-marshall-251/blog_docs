package com.andrew.blog.dtos.errors;

public class RefreshTokenExpiredException extends RuntimeException {
	public RefreshTokenExpiredException() {
		super("Refresh Token Expired");
	}
}

package com.andrew.blog.dtos.errors;

public class PostNotFoundByIdException extends RuntimeException {
	public PostNotFoundByIdException(Long id) {
		super("Post #" + id + " not found.");
	}
}

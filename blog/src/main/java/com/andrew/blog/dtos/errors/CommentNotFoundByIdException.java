package com.andrew.blog.dtos.errors;

public class CommentNotFoundByIdException extends RuntimeException {
	public CommentNotFoundByIdException(Long id) {
		super("Comment #" + id + " not found");
	}
}

package com.andrew.blog.dtos.errors;

public class PostTitleAlreadyTakenException extends RuntimeException {
	public PostTitleAlreadyTakenException(String title) {
		super("Post title: " + title + " already taken");
	}
}

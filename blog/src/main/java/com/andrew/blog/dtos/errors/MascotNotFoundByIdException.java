package com.andrew.blog.dtos.errors;

public class MascotNotFoundByIdException extends RuntimeException {
	public MascotNotFoundByIdException(Long id) {
		super("Mascot #" + id + " not found.");
	}
}

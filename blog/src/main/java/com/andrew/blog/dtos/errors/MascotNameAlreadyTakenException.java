package com.andrew.blog.dtos.errors;

public class MascotNameAlreadyTakenException extends RuntimeException {
	public MascotNameAlreadyTakenException(String name) {
		super("Mascot name: " + name + " is already in use");
	}
}

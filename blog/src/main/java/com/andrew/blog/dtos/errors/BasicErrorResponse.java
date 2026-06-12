package com.andrew.blog.dtos.errors;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BasicErrorResponse {
	private String code;
	private String message;
}

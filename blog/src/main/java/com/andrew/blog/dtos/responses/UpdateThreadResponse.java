package com.andrew.blog.dtos.responses;

import lombok.Setter;
import lombok.Getter;

@Getter @Setter
public class UpdateThreadResponse {
	private Long id;
	private String name;
	private Long postCount;
}

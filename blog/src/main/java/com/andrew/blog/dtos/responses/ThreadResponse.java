package com.andrew.blog.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Getter @Setter
@AllArgsConstructor
public class ThreadResponse {
	private Long id;
	private String name;
	private Long postCount;
}

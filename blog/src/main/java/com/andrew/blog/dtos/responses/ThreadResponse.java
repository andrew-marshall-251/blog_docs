package com.andrew.blog.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ThreadResponse {
	private Long id;
	private String name;
	private Long postCount;
}

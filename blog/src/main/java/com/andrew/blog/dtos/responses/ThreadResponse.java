package com.andrew.blog.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ThreadResponse {
	private Long id;
	private String name;
	private Long postCount;
}

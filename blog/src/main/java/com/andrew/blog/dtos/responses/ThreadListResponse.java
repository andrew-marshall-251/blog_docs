package com.andrew.blog.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ThreadListResponse {
	private List<ThreadResponse> threads;
}

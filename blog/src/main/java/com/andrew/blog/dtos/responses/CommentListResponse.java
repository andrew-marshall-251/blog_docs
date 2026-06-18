package com.andrew.blog.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class CommentListResponse {
	private List<CommentResponse> comments;
}

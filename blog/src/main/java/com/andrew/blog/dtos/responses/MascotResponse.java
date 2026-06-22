package com.andrew.blog.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MascotResponse {
	private Long mascotId;
	private String mascotName;
	private String mascotImgUrl;
}

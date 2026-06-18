package com.andrew.blog.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Getter @Setter
@AllArgsConstructor
public class MascotResponse {
	private Long mascotId;
	private String mascotName;
	private String mascotImgUrl;
}

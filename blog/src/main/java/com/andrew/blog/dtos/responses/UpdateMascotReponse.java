package com.andrew.blog.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateMascotReponse {
	private Long mascotId;
	private String mascotName;
	private String mascotImgUrl;
}

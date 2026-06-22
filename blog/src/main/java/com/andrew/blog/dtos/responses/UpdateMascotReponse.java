package com.andrew.blog.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class UpdateMascotReponse {
	private Long mascotId;
	private String mascotName;
	private String mascotImgUrl;
}

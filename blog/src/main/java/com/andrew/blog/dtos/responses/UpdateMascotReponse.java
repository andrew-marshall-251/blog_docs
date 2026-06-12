package com.andrew.blog.dtos.responses;

import lombok.Setter;
import lombok.Getter;

@Getter @Setter
public class UpdateMascotReponse {
	private Long mascotId;
	private String mascotName;
	private String mascotImgUrl;
}

package com.andrew.blog.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateMascotRequest {
	private String mascotName;
	private String mascotImgUrl;
}

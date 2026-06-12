package com.andrew.blog.dtos.requests;

import lombok.Setter;
import lombok.Getter;

@Getter @Setter
public class CreateMascotRequest {
	private String mascotName;
	private String mascotImgUrl;
}

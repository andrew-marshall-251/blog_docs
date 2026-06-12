package com.andrew.blog.dtos.requests;

import lombok.Setter;
import lombok.Getter;

@Getter @Setter
public class UpdateMascotRequest {
	private String mascotName;
	private String mascotImgUrl;
}

package com.andrew.blog.dtos.requests;

import lombok.Data;

@Data
public class CreateMascotRequest {
	private String mascotName;
	private String mascotImgUrl;
}

package com.andrew.blog.dtos.requests;

import lombok.Data;

@Data
public class UpdateMascotRequest {
	private String mascotName;
	private String mascotImgUrl;
}

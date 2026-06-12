package com.andrew.blog.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateSelfPasswordRequest {
	private String oldPassword;
	private String newPassword;
}

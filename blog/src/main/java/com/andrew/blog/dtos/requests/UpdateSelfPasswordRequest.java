package com.andrew.blog.dtos.requests;

import lombok.Data;

@Data
public class UpdateSelfPasswordRequest {
	private String oldPassword;
	private String newPassword;
}

package com.andrew.blog.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Getter @Setter
@AllArgsConstructor
public class CreateUserRequest {
	@NotBlank
	@Size(min=3, max=30)
	@Pattern(regexp="^[A-Za-z0-9_]+$")
	private String username;

	@NotBlank
	@Email
	@Size(max=254)
	private String email;

	@NotBlank
	@Size(min = 8)
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$")
	private String password;

	@NotNull
	private Long mascotId;

	@NotBlank
	@Size(max=300)
	private String bio;
}

package com.andrew.blog.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class MascotListResponse {
	List<MascotResponse> mascots;
}

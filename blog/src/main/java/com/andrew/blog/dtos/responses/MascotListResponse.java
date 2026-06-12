package com.andrew.blog.dtos.responses;

import lombok.Setter;
import lombok.Getter;

import java.util.List;

@Getter @Setter
public class MascotListResponse {
	List<MascotResponse> mascots;
}

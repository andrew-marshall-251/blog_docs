package com.andrew.blog.services;

import com.andrew.blog.dtos.requests.CreateMascotRequest;
import com.andrew.blog.dtos.requests.UpdateMascotRequest;
import com.andrew.blog.dtos.responses.CreateMascotResponse;
import com.andrew.blog.dtos.responses.MascotListResponse;
import com.andrew.blog.dtos.responses.MascotResponse;
import com.andrew.blog.dtos.responses.UpdateMascotReponse;

public interface MascotService {
	MascotListResponse getAllMascots();
	CreateMascotResponse createMascot(CreateMascotRequest request);
	MascotResponse getMascot(Long id);
	UpdateMascotReponse updateMascot(UpdateMascotRequest request, Long id);
	void deleteMascot(Long id);
}

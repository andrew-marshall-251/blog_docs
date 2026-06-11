package com.andrew.blog.services;

import com.andrew.blog.dtos.requests.CreateMascotRequest;
import com.andrew.blog.dtos.requests.UpdateMascotRequest;
import com.andrew.blog.dtos.responses.CreateMascotResponse;
import com.andrew.blog.dtos.responses.MascotListResponse;
import com.andrew.blog.dtos.responses.MascotResponse;
import com.andrew.blog.dtos.responses.UpdateMascotReponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

public interface MascotService {
	MascotListResponse getAllMascots();
	CreateMascotResponse createMascot(@Valid CreateMascotRequest request, Authentication auth);
	MascotResponse getMascot(Long id);
	UpdateMascotReponse updateMascot(@Valid UpdateMascotRequest request, Authentication auth, Long id);
}

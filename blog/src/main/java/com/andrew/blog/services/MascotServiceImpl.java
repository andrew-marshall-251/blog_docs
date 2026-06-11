package com.andrew.blog.services;

import com.andrew.blog.dtos.requests.CreateMascotRequest;
import com.andrew.blog.dtos.requests.UpdateMascotRequest;
import com.andrew.blog.dtos.responses.CreateMascotResponse;
import com.andrew.blog.dtos.responses.MascotListResponse;
import com.andrew.blog.dtos.responses.MascotResponse;
import com.andrew.blog.dtos.responses.UpdateMascotReponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class MascotServiceImpl implements MascotService {
	@Override
	public MascotListResponse getAllMascots() {
		return null;
	}

	@Override
	public CreateMascotResponse createMascot(CreateMascotRequest request, Authentication auth) {
		return null;
	}

	@Override
	public MascotResponse getMascot(Long id) {
		return null;
	}

	@Override
	public UpdateMascotReponse updateMascot(UpdateMascotRequest request, Authentication auth, Long id) {
		return null;
	}
}

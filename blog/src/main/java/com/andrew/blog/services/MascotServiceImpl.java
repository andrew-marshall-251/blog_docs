package com.andrew.blog.services;

import com.andrew.blog.dtos.errors.MascotNameAlreadyTakenException;
import com.andrew.blog.dtos.errors.MascotNotFoundByIdException;
import com.andrew.blog.dtos.requests.CreateMascotRequest;
import com.andrew.blog.dtos.requests.UpdateMascotRequest;
import com.andrew.blog.dtos.responses.CreateMascotResponse;
import com.andrew.blog.dtos.responses.MascotListResponse;
import com.andrew.blog.dtos.responses.MascotResponse;
import com.andrew.blog.dtos.responses.UpdateMascotReponse;
import com.andrew.blog.entities.Mascot;
import com.andrew.blog.repositories.MascotRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MascotServiceImpl implements MascotService {
	private final MascotRepository mascotRepository;

	public MascotServiceImpl(MascotRepository mascotRepository) {
		this.mascotRepository = mascotRepository;
	}

	@Override
	public MascotListResponse getAllMascots() {
		List<Mascot> mascots = mascotRepository.findAll();
		List<MascotResponse> mascotsResponses = new ArrayList<>();
		for (Mascot mascot: mascots) {
			MascotResponse mascotResponse = new MascotResponse();
			mascotResponse.setMascotId(mascot.getId());
			mascotResponse.setMascotName(mascot.getName());
			mascotResponse.setMascotImgUrl(mascot.getImgUrl());
			mascotsResponses.add(mascotResponse);
		}
		MascotListResponse response = new MascotListResponse();
		response.setMascots(mascotsResponses);
		return response;
	}

	@Override
	public CreateMascotResponse createMascot(
			CreateMascotRequest request) {
		// errors
		if (mascotRepository.existsByName(request.getMascotName())) {
			throw new MascotNameAlreadyTakenException(request.getMascotName());
		}
		// create new mascot
		Mascot newMascot = new Mascot();
		newMascot.setName(request.getMascotName());
		newMascot.setImgUrl(request.getMascotImgUrl());
		mascotRepository.save(newMascot);
		// create response
		CreateMascotResponse response = new CreateMascotResponse();
		response.setMascotId(newMascot.getId());
		response.setMascotName(newMascot.getName());
		response.setMascotImgUrl(newMascot.getImgUrl());
		return response;
	}

	@Override
	public MascotResponse getMascot(Long id) {
		// errors
		Mascot mascot = mascotRepository.findById(id)
				.orElseThrow(() -> new MascotNotFoundByIdException(id));
		// create response
		MascotResponse response = new MascotResponse();
		response.setMascotId(mascot.getId());
		response.setMascotName(mascot.getName());
		response.setMascotImgUrl(mascot.getImgUrl());
		return response;
	}

	@Override
	public UpdateMascotReponse updateMascot(
			UpdateMascotRequest request,
			Long id) {
		// errors
		Mascot mascot = mascotRepository.findById(id)
				.orElseThrow(() -> new MascotNotFoundByIdException(id));
		// update
		if (request.getMascotName() != null) {
			mascot.setName(request.getMascotName());
		}
		if (request.getMascotImgUrl() != null) {
			mascot.setImgUrl(request.getMascotImgUrl());
		}
		mascotRepository.save(mascot);
		// create response
		UpdateMascotReponse response = new UpdateMascotReponse();
		response.setMascotId(mascot.getId());
		response.setMascotName(mascot.getName());
		response.setMascotImgUrl(mascot.getImgUrl());
		return response;
	}
}

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
import java.util.stream.Collectors;

@Service
public class MascotServiceImpl implements MascotService {
	private final MascotRepository mascotRepository;

	public MascotServiceImpl(MascotRepository mascotRepository) {
		this.mascotRepository = mascotRepository;
	}

	@Override
	public MascotListResponse getAllMascots() {
		List<Mascot> mascots = mascotRepository.findAll();
		mascots = mascots.stream()
				.filter(mascot -> !mascot.getName().equals("[deletedMascot]"))
				.collect(Collectors.toList());
		List<MascotResponse> mascotsResponses = mascots.stream()
				.map(mascot -> new MascotResponse(
						mascot.getId(),
						mascot.getName(),
						mascot.getImgUrl()))
				.collect(Collectors.toList());
		return new MascotListResponse(mascotsResponses);
	}

	@Override
	public CreateMascotResponse createMascot(
			CreateMascotRequest request) {
		// errors
		if (mascotRepository.existsByName(request.getMascotName())) {
			throw new MascotNameAlreadyTakenException(request.getMascotName());
		}
		// create new mascot
		Mascot newMascot = new Mascot(
			request.getMascotName(),
			request.getMascotImgUrl());
		mascotRepository.save(newMascot);
		// create response
		CreateMascotResponse response = new CreateMascotResponse(
				newMascot.getId(),
				newMascot.getName(),
				newMascot.getImgUrl());
		return response;
	}

	@Override
	public MascotResponse getMascot(Long id) {
		// errors
		Mascot mascot = mascotRepository.findById(id)
				.orElseThrow(() -> new MascotNotFoundByIdException(id));
		if (mascot.getName().equals("[deletedMascot]")) { // hide the soft deletes
			throw new MascotNotFoundByIdException(id);
		}
		// create response
		MascotResponse response = new MascotResponse(
				mascot.getId(),
				mascot.getName(),
				mascot.getImgUrl());
		return response;
	}

	@Override
	public UpdateMascotReponse updateMascot(
			UpdateMascotRequest request,
			Long id) {
		System.out.println("hi");
		// errors
		Mascot mascot = mascotRepository.findById(id)
				.orElseThrow(() -> new MascotNotFoundByIdException(id));
		if (mascotRepository.existsByName(request.getMascotName()) ||
		request.getMascotName() == "[deletedMascot]") { // reserved
			throw new MascotNameAlreadyTakenException(request.getMascotName());
		}
		// update
		if (request.getMascotName() != null) {
			mascot.setName(request.getMascotName());
		}
		if (request.getMascotImgUrl() != null) {
			mascot.setImgUrl(request.getMascotImgUrl());
		}
		mascotRepository.save(mascot);
		// create response
		UpdateMascotReponse response = new UpdateMascotReponse(
				mascot.getId(),
				mascot.getName(),
				mascot.getImgUrl());
		return response;
	}

	@Override
	public void deleteMascot(Long id) {
		// errors
		Mascot mascot = mascotRepository.findById(id)
				.orElseThrow(() -> new MascotNotFoundByIdException(id));
		mascot.setName("[deletedMascot]");
		mascotRepository.save(mascot);
	}
}

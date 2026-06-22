package com.andrew.blog.controllers;

import com.andrew.blog.dtos.requests.CreateMascotRequest;
import com.andrew.blog.dtos.requests.UpdateMascotRequest;
import com.andrew.blog.dtos.responses.CreateMascotResponse;
import com.andrew.blog.dtos.responses.MascotListResponse;
import com.andrew.blog.dtos.responses.MascotResponse;
import com.andrew.blog.dtos.responses.UpdateMascotReponse;
import com.andrew.blog.services.MascotService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
public class MascotController {
	private final MascotService mascotService;

	public MascotController(MascotService mascotService) {
		this.mascotService = mascotService;
	}

	@GetMapping("/mascots")
	public ResponseEntity<MascotListResponse> getAllMascots() {
		MascotListResponse response = mascotService.getAllMascots();
		return ResponseEntity.ok(response);
	}

	@PostMapping("/mascots")
	public ResponseEntity<CreateMascotResponse> createMascot(
			@Valid @RequestBody CreateMascotRequest request,
			Authentication auth) {
		CreateMascotResponse response = mascotService.createMascot(request);
		URI location = URI.create("/api/v1/mascots/" + response.getMascotId());
		return ResponseEntity.created(location).body(response);
	}

	@GetMapping("/mascots/{mascot_id}")
	public ResponseEntity<MascotResponse> getMascot(
			@PathVariable("mascot_id") Long id) {
		MascotResponse response = mascotService.getMascot(id);
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/mascots/{mascot_id}")
	public ResponseEntity<UpdateMascotReponse> updateMascot(
			@Valid @RequestBody UpdateMascotRequest request,
			Authentication auth,
			@PathVariable("mascot_id") Long id) {
		UpdateMascotReponse response = mascotService.updateMascot(request, id);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/mascots/{mascot_id}")
	public ResponseEntity<CreateMascotResponse> createMascot(
			@PathVariable("mascot_id") Long id,
			Authentication auth) {
		mascotService.deleteMascot(id);
		return ResponseEntity.noContent().build();
	}
}

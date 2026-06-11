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

@RestController
@RequestMapping("/api")
public class MascotController {
	private final MascotService mascotService;

	public MascotController(MascotService mascotService) {
		this.mascotService = mascotService;
	}

	@GetMapping("/mascots")
	public ResponseEntity<MascotListResponse> getAllMascots() {
		MascotListResponse response = mascotService.getAllMascots();
		return ResponseEntity.body(response);
	}

	@PostMapping("/mascots")
	public ResponseEntity<CreateMascotResponse> createMascot(
			@Valid @RequestBody CreateMascotRequest request,
			Authentication auth) {
		CreateMascotResponse response = mascotService.createMascot(request, auth);
		return ResponseEntity.body(response);
	}

	@GetMapping("/mascots/{mascot_id}")
	public ResponseEntity<MascotResponse> getMascot(
			@PathVariable("mascot_id") Long id) {
		MascotResponse response = mascotService.getMascot(id);
		return ResponseEntity.body(response);
	}

	@PatchMapping("/mascots/{mascot_id}")
	public ResponseEntity<UpdateMascotReponse> updateMascot(
			@Valid @RequestBody UpdateMascotRequest request,
			Authentication auth,
			@PathVariable("mascot_id") Long id) {
		UpdateMascotReponse response = mascotService.updateMascot(request, auth, id);
		return ResponseEntity.body(response);
	}
}

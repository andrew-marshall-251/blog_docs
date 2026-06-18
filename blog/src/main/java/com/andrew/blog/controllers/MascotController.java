package com.andrew.blog.controllers;

import com.andrew.blog.dtos.errors.IsNotAdminException;
import com.andrew.blog.dtos.requests.CreateMascotRequest;
import com.andrew.blog.dtos.requests.UpdateMascotRequest;
import com.andrew.blog.dtos.responses.CreateMascotResponse;
import com.andrew.blog.dtos.responses.MascotListResponse;
import com.andrew.blog.dtos.responses.MascotResponse;
import com.andrew.blog.dtos.responses.UpdateMascotReponse;
import com.andrew.blog.services.MascotService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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

	private void adminVerification(Authentication auth) {
		boolean isAdmin = auth
				.getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
		if (!isAdmin) {
			throw new IsNotAdminException(auth.getName());
		}
	}

	@GetMapping("/mascots")
	public ResponseEntity<MascotListResponse> getAllMascots() {
		MascotListResponse response = mascotService.getAllMascots();
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(response);
	}

	@PostMapping("/mascots")
	public ResponseEntity<CreateMascotResponse> createMascot(
			@Valid @RequestBody CreateMascotRequest request,
			Authentication auth) {
		adminVerification(auth);
		CreateMascotResponse response = mascotService.createMascot(request);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(response);
	}

	@GetMapping("/mascots/{mascot_id}")
	public ResponseEntity<MascotResponse> getMascot(
			@PathVariable("mascot_id") Long id) {
		MascotResponse response = mascotService.getMascot(id);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(response);
	}

	@PatchMapping("/mascots/{mascot_id}")
	public ResponseEntity<UpdateMascotReponse> updateMascot(
			@Valid @RequestBody UpdateMascotRequest request,
			Authentication auth,
			@PathVariable("mascot_id") Long id) {
		System.out.println("before_auth");
		adminVerification(auth);
		System.out.println("after_auth");
		UpdateMascotReponse response = mascotService.updateMascot(request, id);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(response);
	}

	@DeleteMapping("/mascots/{mascot_id}")
	public ResponseEntity<CreateMascotResponse> createMascot(
			@PathVariable("mascot_id") Long id,
			Authentication auth) {
		adminVerification(auth);
		mascotService.deleteMascot(id);
		return ResponseEntity.noContent().build();
	}
}

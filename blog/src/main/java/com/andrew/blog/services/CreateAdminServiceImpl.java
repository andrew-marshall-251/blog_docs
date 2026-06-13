package com.andrew.blog.services;

import com.andrew.blog.dtos.errors.EmailAlreadyTakenException;
import com.andrew.blog.dtos.errors.MascotNotFoundByIdException;
import com.andrew.blog.dtos.errors.UsernameAlreadyTakenException;
import com.andrew.blog.dtos.requests.CreateUserRequest;
import com.andrew.blog.dtos.responses.CreateUserResponse;
import com.andrew.blog.entities.Mascot;
import com.andrew.blog.entities.Role;
import com.andrew.blog.entities.User;
import com.andrew.blog.repositories.MascotRepository;
import com.andrew.blog.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateAdminServiceImpl implements CreateAdminService {
	private final MascotRepository mascotRepository;
	private final PasswordEncoder encoder;
	private UserRepository userRepository;

	public CreateAdminServiceImpl(UserRepository userRepository, MascotRepository mascotRepository, PasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.mascotRepository = mascotRepository;
		this.encoder = encoder;
	}
	@Override
	public CreateUserResponse createAdmin(
			@Valid @RequestBody CreateUserRequest request) {
		if (userRepository.existsByUsername(request.getUsername())) {
			throw new UsernameAlreadyTakenException(request.getUsername());
		}
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new EmailAlreadyTakenException(request.getEmail());
		}
		// create new user
		User newUser = new User();
		newUser.setUsername(request.getUsername());
		newUser.setEmail(request.getEmail());
		Mascot mascot = mascotRepository.findById(request.getMascotId())
				.orElseThrow(() -> new MascotNotFoundByIdException(request.getMascotId()));
		newUser.setMascot(mascot);
		String passwordHash = encoder.encode(request.getPassword());
		System.out.println(passwordHash);
		System.out.println(request.getPassword());
		newUser.setPasswordHash(passwordHash);
		newUser.setBio(request.getBio());
		List<Role> roles = new ArrayList<>();
		roles.add(Role.ROLE_USER);
		roles.add(Role.ROLE_ADMIN);
		newUser.setRoles(roles);
		userRepository.save(newUser);
		// create response
		CreateUserResponse response = new CreateUserResponse();
		response.setUserId(newUser.getId());
		response.setUsername(newUser.getUsername());
		response.setEmail(newUser.getEmail());
		response.setMascotId(mascot.getId());
		response.setBio(newUser.getBio());
		return response;
	}
}

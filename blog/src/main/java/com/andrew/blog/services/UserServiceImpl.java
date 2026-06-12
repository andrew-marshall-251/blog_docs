package com.andrew.blog.services;

import com.andrew.blog.dtos.errors.*;
import com.andrew.blog.dtos.requests.CreateUserRequest;
import com.andrew.blog.dtos.responses.CreateUserResponse;
import com.andrew.blog.dtos.responses.PostListResponse;
import com.andrew.blog.dtos.responses.PostResponse;
import com.andrew.blog.dtos.responses.UserResponse;
import com.andrew.blog.entities.Mascot;
import com.andrew.blog.entities.Post;
import com.andrew.blog.entities.User;
import com.andrew.blog.repositories.MascotRepository;
import com.andrew.blog.repositories.PostRepository;
import com.andrew.blog.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final MascotRepository mascotRepository;
	private final PasswordEncoder encoder;

	public UserServiceImpl(
			UserRepository userRepository,
			MascotRepository mascotRepository,
			PostRepository postRepository,
			PasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.mascotRepository = mascotRepository;
		this.encoder = encoder;
	}

	@Override
	public CreateUserResponse createUser(CreateUserRequest request) {
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
		newUser.setPasswordHash(passwordHash);
		newUser.setBio(request.getBio());
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

	@Override
	public UserResponse getUser(Long id) {
		// errors
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundByIdException(id));
		// create response
		UserResponse response = new UserResponse();
		response.setUserId(user.getId());
		response.setUsername(user.getUsername());
		response.setBio(user.getBio());
		Mascot mascot = user.getMascot();
		response.setMascotId(mascot.getId());
		return response;
	}

	@Override
	public void deleteUser(Long id) {
		// errors
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundByIdException(id));
		// delete
		userRepository.delete(user);
	}
}

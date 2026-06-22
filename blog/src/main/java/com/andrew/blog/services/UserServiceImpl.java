package com.andrew.blog.services;

import com.andrew.blog.dtos.errors.EmailAlreadyTakenException;
import com.andrew.blog.dtos.errors.MascotNotFoundByIdException;
import com.andrew.blog.dtos.errors.UserNotFoundByIdException;
import com.andrew.blog.dtos.errors.UsernameAlreadyTakenException;
import com.andrew.blog.dtos.requests.CreateUserRequest;
import com.andrew.blog.dtos.responses.CreateUserResponse;
import com.andrew.blog.dtos.responses.UserResponse;
import com.andrew.blog.entities.Mascot;
import com.andrew.blog.entities.Role;
import com.andrew.blog.entities.User;
import com.andrew.blog.repositories.*;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final MascotRepository mascotRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final PasswordEncoder encoder;
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;

	public UserServiceImpl(
			UserRepository userRepository,
			MascotRepository mascotRepository,
			RefreshTokenRepository refreshTokenRepository,
			PasswordEncoder encoder, PostRepository postRepository, CommentRepository commentRepository) {
		this.userRepository = userRepository;
		this.mascotRepository = mascotRepository;
		this.refreshTokenRepository = refreshTokenRepository;
		this.encoder = encoder;
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
	}

	public void validateUsername(String username) {
		if (userRepository.existsByUsername(username)) {
			throw new UsernameAlreadyTakenException(username);
		}
		if (username.equals("[deletedUser]")) {
			throw new UsernameAlreadyTakenException(username);
		}
	}

	public void validateEmail(String email) {
		if (userRepository.existsByEmail(email)) {
			throw new EmailAlreadyTakenException(email);
		}
		if (email.equals("deleted@user.com")) {
			throw new EmailAlreadyTakenException(email);
		}
	}

	@Override
	public User getUserFromRequest(CreateUserRequest request, boolean isAdmin) {
		validateUsername(request.getUsername());
		validateEmail(request.getEmail());
		Mascot mascot = mascotRepository.findById(request.getMascotId())
				.orElseThrow(() -> new MascotNotFoundByIdException(request.getMascotId()));
		// create new user
		List<Role> roles = new ArrayList<>();
		roles.add(Role.ROLE_USER);
		if (isAdmin) {
			roles.add(Role.ROLE_ADMIN);
		}
		String passwordHash = encoder.encode(request.getPassword());

		return new User(
				request.getUsername(),
				request.getEmail(),
				mascot,
				request.getBio(),
				passwordHash,
				roles);
	}

	@Override
	public User getUserFromRequest(CreateUserRequest request) {
		return getUserFromRequest(request, false);
	}

	@Override
	public void addFirstAdmin() {
		CreateUserRequest request = new CreateUserRequest(
				"THE_ADMIN",
				"admin@admin.com",
				System.getenv("ADMIN_PASSWORD"),
				1L,
				"hard coded admin on boot");
		if (!userRepository.existsByUsername("THE_ADMIN")) {
			User user = getUserFromRequest(request, true);
			userRepository.save(user);
		}
	}

	@Override
	@Transactional
	public CreateUserResponse createUser(
			@Valid @RequestBody CreateUserRequest request) {
		User newUser = getUserFromRequest(request);
		userRepository.save(newUser);
		// create response
		CreateUserResponse response = new CreateUserResponse(
			newUser.getId(),
			newUser.getUsername(),
			newUser.getEmail(),
			newUser.getMascot().getId(),
			newUser.getBio());
		return response;
	}

	@Override
	public UserResponse getUser(Long id) {
		// errors
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundByIdException(id));
		if (user.getUsername().equals("[deletedUser]")) {
				throw new UserNotFoundByIdException(id);
		}
		// create response
		UserResponse response = new UserResponse(
				user.getId(),
				user.getUsername(),
				user.getMascot().getId(),
				user.getBio());
		return response;
	}

	@Override
	@Transactional
	public void deleteUser(Long id) {
		// errors
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundByIdException(id));
		refreshTokenRepository.deleteByUserId(user.getId());
		// soft delete to keep the comments
		user.setUsername("[deletedUser]");
		user.setEmail("deleted@user.com");
		userRepository.save(user);
	}
}

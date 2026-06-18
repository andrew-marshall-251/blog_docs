package com.andrew.blog.services;

import com.andrew.blog.dtos.errors.MascotNotFoundByIdException;
import com.andrew.blog.dtos.errors.UserNotFoundByUsernameException;
import com.andrew.blog.dtos.requests.UpdateSelfPasswordRequest;
import com.andrew.blog.dtos.requests.UpdateSelfRequest;
import com.andrew.blog.dtos.responses.PostExcerptResponse;
import com.andrew.blog.dtos.responses.PostListResponse;
import com.andrew.blog.dtos.responses.SelfResponse;
import com.andrew.blog.dtos.responses.UpdateSelfResponse;
import com.andrew.blog.entities.Mascot;
import com.andrew.blog.entities.Post;
import com.andrew.blog.entities.User;
import com.andrew.blog.repositories.MascotRepository;
import com.andrew.blog.repositories.PostRepository;
import com.andrew.blog.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeServiceImpl implements MeService {
	private final UserRepository userRepository;
	private final MascotRepository mascotRepository;
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder encoder;
	private final PostRepository postRepository;
	private final PostService postService;

	public MeServiceImpl(
			UserRepository userRepository,
			MascotRepository mascotRepository,
			AuthenticationManager authenticationManager,
			PasswordEncoder encoder,
			PostRepository postRepository,
			PostService postService) {
		this.userRepository = userRepository;
		this.mascotRepository = mascotRepository;
		this.authenticationManager = authenticationManager;
		this.encoder = encoder;
		this.postRepository = postRepository;
		this.postService = postService;
	}

	@Override
	public SelfResponse getSelf(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundByUsernameException(username));
		// create response
		SelfResponse response = new SelfResponse(
				user.getId(),
				user.getUsername(),
				user.getEmail(),
				user.getMascot().getId(),
				user.getBio());
		return response;
	}

	@Override
	public UpdateSelfResponse updateSelf(
			UpdateSelfRequest request,
			String username) {
		// errors
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundByUsernameException(username));
		// update
		if (request.getUsername() != null) {
			user.setUsername(request.getUsername());
		}
		if (request.getEmail() != null) {
			user.setEmail(request.getEmail());
		}
		if (request.getMascotId() != null) {
			Mascot mascot = mascotRepository.findById(request.getMascotId())
					.orElseThrow(() -> new MascotNotFoundByIdException(request.getMascotId()));
			user.setMascot(mascot);
		}
		if (request.getBio() != null) {
			user.setBio(request.getBio());
		}
		userRepository.save(user);
		// create response
		UpdateSelfResponse response = new UpdateSelfResponse(
				user.getId(),
				user.getUsername(),
				user.getEmail(),
				user.getMascot().getId(),
				user.getBio());
		return response;
	}

	@Override
	public void deleteSelf(String username) {
		// errors
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundByUsernameException(username));
		// delete
		userRepository.delete(user);
	}

	@Override
	public UpdateSelfResponse updateSelfPassword(
			UpdateSelfPasswordRequest request,
			String username) {
		// errors
		Authentication authRequest =
				new UsernamePasswordAuthenticationToken(
						username,
						request.getOldPassword());
		authenticationManager.authenticate(authRequest);
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundByUsernameException(username));
		// update password
		String passwordHash = encoder.encode(request.getNewPassword());
		user.setPasswordHash(passwordHash);
		userRepository.save(user);
		// create response
		UpdateSelfResponse response = new UpdateSelfResponse(
				user.getId(),
				user.getUsername(),
				user.getEmail(),
				user.getMascot().getId(),
				user.getBio());
		return response;
	}

	@Override
	public PostListResponse getSelfPosts(String username) {
		List<Post> posts = postRepository.findByAuthorUsername(username);
		return postService.getPostListResponseFromPosts(posts);
	}
}

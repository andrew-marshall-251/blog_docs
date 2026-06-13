package com.andrew.blog.services;

import com.andrew.blog.dtos.errors.UsernameOrEmailNotFoundException;
import com.andrew.blog.dtos.requests.LoginRequest;
import com.andrew.blog.dtos.responses.LoginResponse;
import com.andrew.blog.entities.Role;
import com.andrew.blog.entities.User;
import com.andrew.blog.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
	private UserRepository userRepository;
	private AuthenticationManager authenticationManager;
	private PasswordEncoder encoder;
	private SecretKey signingKey;

	public AuthServiceImpl (
			UserRepository userRepository,
			AuthenticationManager authenticationManager,
			SecretKey signingKey,
			PasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.authenticationManager = authenticationManager;
		this.signingKey = signingKey;
		this.encoder = encoder;
	}
	@Override
	public LoginResponse login(LoginRequest request) {
		Authentication authRequest =
				new UsernamePasswordAuthenticationToken(
						request.getUsernameOrEmail(),
						request.getPassword());
		authenticationManager.authenticate(authRequest);
		User user = userRepository.findByUsernameOrEmail(
						request.getUsernameOrEmail(),
						request.getUsernameOrEmail())
				.orElseThrow(() -> new UsernameOrEmailNotFoundException(request.getUsernameOrEmail()));
		List<Role> roles = user.getRoles();

		Instant now = Instant.now();
		Instant exp = now.plusSeconds(1000);

		String accessToken = Jwts.builder()
				.subject(user.getUsername())
				.claim("roles", roles)
				.issuedAt(Date.from(now))
				.expiration(Date.from(exp))
				.signWith(signingKey, Jwts.SIG.HS256)
				.compact();
		// create response
		LoginResponse response = new LoginResponse();
		response.setId(user.getId());
		response.setAccessToken(accessToken);
		response.setTokenType("Bearer");
		response.setExpiresIn(1000L);
		response.setUsername(user.getUsername());
		response.setRoles(roles);
		return response;
	}
}

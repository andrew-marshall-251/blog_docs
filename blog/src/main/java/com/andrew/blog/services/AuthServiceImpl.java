package com.andrew.blog.services;

import com.andrew.blog.dtos.errors.RefreshDoesNotExistException;
import com.andrew.blog.dtos.errors.RefreshTokenExpiredException;
import com.andrew.blog.dtos.errors.UserNotFoundByUsernameException;
import com.andrew.blog.dtos.errors.UsernameOrEmailNotFoundException;
import com.andrew.blog.dtos.requests.LoginRequest;
import com.andrew.blog.dtos.requests.RefreshRequest;
import com.andrew.blog.dtos.responses.LoginResponse;
import com.andrew.blog.dtos.responses.RefreshResponse;
import com.andrew.blog.dtos.responses.TokenResponse;
import com.andrew.blog.entities.RefreshToken;
import com.andrew.blog.entities.Role;
import com.andrew.blog.entities.User;
import com.andrew.blog.repositories.RefreshTokenRepository;
import com.andrew.blog.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
	private final Long expJwt = Long.valueOf(System.getenv("JWT_EXP"));
	private final Long expRefresh = Long.valueOf(System.getenv("REFRESH_EXP"));

	private final RefreshTokenRepository refreshTokenRepository;
	private final UserRepository userRepository;
	private final AuthenticationManager authenticationManager;
	private final SecretKey signingKey;

	public AuthServiceImpl (
			UserRepository userRepository,
			AuthenticationManager authenticationManager,
			SecretKey signingKey,
			RefreshTokenRepository refreshTokenRepository) {
		this.userRepository = userRepository;
		this.authenticationManager = authenticationManager;
		this.signingKey = signingKey;
		this.refreshTokenRepository = refreshTokenRepository;
	}

	public String sha256(String s) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(s.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(hash);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String generateRefreshToken(User user, Long expiresIn) {
		Instant now = Instant.now();
		Instant expRefresh = now.plusSeconds(expiresIn); // one month
		byte[] bytes = new byte[32];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(bytes);
		String refreshTokenRaw = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
		RefreshToken refreshToken = new RefreshToken(
				user,
				sha256(refreshTokenRaw),
				expRefresh);
		refreshTokenRepository.save(refreshToken);
		return refreshTokenRaw;
	}

	private String generateJwt(User user, List<Role> roles, Long expiresIn) {
		Instant now = Instant.now();
		Instant expJwt = now.plusSeconds(expiresIn);
		String accessToken = Jwts.builder()
				.subject(user.getUsername())
				.claim("roles", roles.stream().map(Role::name).collect(Collectors.toList()))
				.issuedAt(Date.from(now))
				.expiration(Date.from(expJwt))
				.signWith(signingKey, Jwts.SIG.HS256)
				.compact();
		return accessToken;
	}

	@Override
	@Transactional
	public LoginResponse login(LoginRequest request) {
		Authentication authRequest = new UsernamePasswordAuthenticationToken(
						request.getUsernameOrEmail(),
						request.getPassword());
		authenticationManager.authenticate(authRequest);
		// response
		User user = userRepository.findByUsernameOrEmail(
						request.getUsernameOrEmail(),
						request.getUsernameOrEmail())
				.orElseThrow(() -> new UsernameOrEmailNotFoundException(request.getUsernameOrEmail()));

		String accessToken = generateJwt(user, user.getRoles(), expJwt);
		String refreshTokenRaw = generateRefreshToken(user, expRefresh);
		LoginResponse response = new LoginResponse(
				user.getId(),
				new TokenResponse(accessToken, "Bearer", expJwt),
				new TokenResponse(refreshTokenRaw, "Refresh", expRefresh),
				user.getUsername(),
				user.getRoles());
		return response;
	}

	@Override
	@Transactional
	public RefreshResponse refresh(RefreshRequest request) {
		RefreshToken refreshToken = refreshTokenRepository.findByRefreshTokenHash(sha256(request.getRefreshToken()))
				.orElseThrow(() -> new RefreshDoesNotExistException("Refresh Token does not exist"));
		// check expiry
		Instant now = Instant.now();
		if (now.isAfter(refreshToken.getExpires())) {
			throw new RefreshTokenExpiredException();
		}
		User user = refreshToken.getUser();
		String accessToken = generateJwt(user, user.getRoles(), expJwt);
		return new RefreshResponse(
				accessToken,
				"Bearer",
				expJwt);
	}

	@Override
	@Transactional
	public void revokeRefresh(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundByUsernameException(username));
		List<RefreshToken>	tokens = refreshTokenRepository.findByUserId(user.getId());
		tokens.stream().forEach(refreshTokenRepository::delete);
	}
}

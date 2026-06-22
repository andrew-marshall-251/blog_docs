package com.andrew.blog.config;

import com.andrew.blog.repositories.UserRepository;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
		http
				.cors(Customizer.withDefaults())
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session ->
						session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						// auth controller
						.requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/v1/auth/refresh").permitAll()
						.requestMatchers(HttpMethod.DELETE, "/api/v1/auth/refresh").authenticated()
						// create admin controller
						// REMEMBER TO CHANGE BEFORE DEPLOYMENT
						// REMEMBER TO CHANGE BEFORE DEPLOYMENT
//						.requestMatchers(HttpMethod.POST, "/api/v1/auth/admins").hasRole("ADMIN")
						.requestMatchers(HttpMethod.POST, "/api/v1/auth/admins").permitAll()
						// mascot controller
						.requestMatchers(HttpMethod.GET, "/api/v1/mascots").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/v1/mascots").hasRole("ADMIN")
						// me controller
						.requestMatchers(HttpMethod.GET, "/api/v1/users/me").authenticated()
						.requestMatchers(HttpMethod.GET, "/api/v1/users/me/posts").authenticated()
						.requestMatchers(HttpMethod.PATCH, "/api/v1/users/me").authenticated()
						.requestMatchers(HttpMethod.PATCH, "/api/v1/users/me/password").authenticated()
						.requestMatchers(HttpMethod.DELETE, "/api/v1/users/me").authenticated()
						// post controller
						.requestMatchers(HttpMethod.GET, "/api/v1/posts").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/v1/posts").authenticated()
						// thread controller
						.requestMatchers(HttpMethod.GET, "/api/v1/threads").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/v1/threads").hasRole("ADMIN")
						// user controller
						.requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
						//==================
						// WILD CARD SECTION
						//==================
						// comments controller
						.requestMatchers(HttpMethod.GET, "/api/v1/posts/*/comments").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/v1/posts/*/comments").authenticated()
						.requestMatchers(HttpMethod.GET, "/api/v1/comments/**").permitAll()
						.requestMatchers(HttpMethod.PATCH, "/api/v1/comments/**").authenticated()
						.requestMatchers(HttpMethod.DELETE, "/api/v1/comments/**").authenticated()
						// mascot controller
						.requestMatchers(HttpMethod.GET, "/api/v1/mascots/**").permitAll()
						.requestMatchers(HttpMethod.PATCH, "/api/v1/mascots/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/api/v1/mascots/**").hasRole("ADMIN")
						// post controller
						.requestMatchers(HttpMethod.GET, "/api/v1/posts/**").permitAll()
						.requestMatchers(HttpMethod.PATCH, "/api/v1/posts/**").authenticated()
						.requestMatchers(HttpMethod.DELETE, "/api/v1/posts/**").authenticated()
						.requestMatchers(HttpMethod.GET, "/api/v1/users/*/posts").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/v1/threads/*/posts").permitAll()
						// thread controller
						.requestMatchers(HttpMethod.GET, "/api/v1/threads/**").permitAll()
						.requestMatchers(HttpMethod.PATCH, "/api/v1/threads/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/api/v1/threads/**").hasRole("ADMIN")
						// user controller
						.requestMatchers(HttpMethod.GET, "/api/v1/users/**").permitAll()
						.requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasRole("ADMIN")
						// close the rest of the gateway
						.anyRequest().denyAll())
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	SecretKey jwtSigningKey(@Value("${app.jwt.secret}") String secret) {
		return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList(
				"http://localhost:5173",
				"http://127.0.0.1:5173",
				"null"
		));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(Arrays.asList("*"));
		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService users(UserRepository userRepository) {
		return username -> {
			com.andrew.blog.entities.User user = userRepository.findByUsernameOrEmail(username, username)
					.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
			return User.builder()
					.username(user.getUsername())
					.password(user.getPasswordHash())
					.authorities(user.getRoles()
								.stream()
								.map(role -> new SimpleGrantedAuthority(role.name()))
								.collect(Collectors.toList()))
						.build();
			};
	}
}

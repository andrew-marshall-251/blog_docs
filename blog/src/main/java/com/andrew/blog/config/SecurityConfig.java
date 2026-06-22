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
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
		http
				.cors(Customizer.withDefaults())
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session ->
						session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(HttpMethod.GET,"/api/v1/comments/**").permitAll()
						.requestMatchers(HttpMethod.GET,"/api/v1/mascots/**").permitAll()
						.requestMatchers(HttpMethod.GET,"/api/v1/posts/**").permitAll()
						.requestMatchers(HttpMethod.GET,"/api/v1/threads/**").permitAll()
						.requestMatchers(HttpMethod.GET,"/api/v1/users/**").permitAll()
						.requestMatchers(HttpMethod.POST,"/api/v1/auth/**").permitAll()
						.requestMatchers(HttpMethod.POST,"/api/v1/comments/**").permitAll()
						.requestMatchers(HttpMethod.POST,"/api/v1/posts/**").permitAll()
						.requestMatchers(HttpMethod.POST,"/api/v1/users/**").permitAll()
						.requestMatchers("/api/v1/**").authenticated()
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

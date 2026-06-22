package com.andrew.blog.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	private final SecretKey signingKey;

	public JwtAuthFilter(SecretKey signingKey) {
		this.signingKey = signingKey;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer ")) {
			String token = header.substring(7);
			try {
				Claims claims = Jwts.parser()
						.verifyWith(signingKey)
						.build()
						.parseSignedClaims(token)
						.getPayload();

				String username = claims.getSubject();

				List<?> roles = claims.get("roles", List.class);
				if (roles == null) {
					roles = Collections.emptyList();
				}

				List<SimpleGrantedAuthority> authorities = roles.stream()
						.map(Object::toString)
						.map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList());

				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
				SecurityContextHolder.getContext().setAuthentication(auth);
			} catch (Exception e) {
				SecurityContextHolder.clearContext();
			}
		}

		chain.doFilter(request, response);
	}
}

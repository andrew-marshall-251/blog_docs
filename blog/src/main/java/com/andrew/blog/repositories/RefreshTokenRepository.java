package com.andrew.blog.repositories;

import com.andrew.blog.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	List<RefreshToken> findByUserId(Long id);
	Optional<RefreshToken> findByRefreshTokenHash(String s);
}

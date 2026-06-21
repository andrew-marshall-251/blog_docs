package com.andrew.blog.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
public class RefreshToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;

	@Column(name = "refresh_token_hash", nullable = false)
	private String refreshTokenHash;

	@Column(name = "expires", nullable = false)
	private Instant expires;

	public RefreshToken(
			User user,
			String refreshTokenHash,
			Instant expires) {
		this.user = user;
		this.refreshTokenHash = refreshTokenHash;
		this.expires = expires;
	}
}

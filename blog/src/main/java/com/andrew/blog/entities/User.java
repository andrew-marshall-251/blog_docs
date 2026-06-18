package com.andrew.blog.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(unique = true, nullable = false)
	private String email;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mascot_id")
	private Mascot mascot;

	@Lob
	private String bio;

	@Column(name = "password_hash", nullable = false)
	private String passwordHash;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private List<Role> roles;

	public User(
			String username,
			String email,
			Mascot mascot,
			String bio,
			String passwordHash,
			List<Role> roles) {
		this.username = username;
		this.email = email;
		this.mascot = mascot;
		this.bio = bio;
		this.passwordHash = passwordHash;
		this.roles = roles;
	}
}

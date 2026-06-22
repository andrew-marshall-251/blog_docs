package com.andrew.blog.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mascots")
@Getter
@Setter
@NoArgsConstructor
public class Mascot {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String imgUrl;

	public Mascot(String name, String imgUrl) {
		this.name = name;
		this.imgUrl = imgUrl;
	}
}

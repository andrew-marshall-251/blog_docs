package com.andrew.blog.repositories;

import com.andrew.blog.entities.Mascot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MascotRepository extends JpaRepository<Mascot, Long> {
}

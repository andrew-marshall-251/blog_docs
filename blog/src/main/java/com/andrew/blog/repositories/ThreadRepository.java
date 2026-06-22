package com.andrew.blog.repositories;

import com.andrew.blog.entities.Thread;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadRepository extends JpaRepository<Thread, Long> {
	boolean existsByName(String name);
}

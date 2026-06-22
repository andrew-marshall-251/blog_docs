package com.andrew.blog.repositories;

import com.andrew.blog.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findByAuthorUsername(String username);
	List<Post> findByAuthorId(Long authorId);
	List<Post> findByThreadId(Long threadId);
	boolean existsByAuthorIdAndTitle(Long authorId, String title);
	Long countByThreadId(Long id);
}

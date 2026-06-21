package com.andrew.blog.repositories;

import com.andrew.blog.entities.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findByAuthorUsername(String username);
	List<Post> findByAuthorId(Long authorId);
	List<Post> findByThreadId(Long threadId);
	boolean existsByAuthorIdAndTitle(Long authorId, String title);
	Long countByThreadId(Long id);
}

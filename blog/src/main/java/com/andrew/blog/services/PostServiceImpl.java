package com.andrew.blog.services;

import com.andrew.blog.dtos.errors.*;
import com.andrew.blog.dtos.requests.CreatePostRequest;
import com.andrew.blog.dtos.requests.UpdatePostRequest;
import com.andrew.blog.dtos.responses.*;
import com.andrew.blog.entities.Post;
import com.andrew.blog.entities.Status;
import com.andrew.blog.entities.Thread;
import com.andrew.blog.entities.User;
import com.andrew.blog.repositories.PostRepository;
import com.andrew.blog.repositories.ThreadRepository;
import com.andrew.blog.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
	private final UserRepository userRepository;
	private final ThreadRepository threadRepository;
	private final PostRepository postRepository;

	public PostServiceImpl(UserRepository userRepository, ThreadRepository threadRepository, PostRepository postRepository) {
		this.userRepository = userRepository;
		this.threadRepository = threadRepository;
		this.postRepository = postRepository;
	}

	private String makeSlug(String title) {
		return Normalizer.normalize(title.trim(), Normalizer.Form.NFKD)
				.toLowerCase(Locale.ROOT)
				.replaceAll("\\s+", "-");
	}

	private String makeExcerpt(String body) {
		if (body.length() <= 100) {
			return body;
		}
		return body.substring(0, 100) + "...";
	}

	public PostListResponse getPostListResponseFromPosts(List<Post> posts) {
		// take out drafts
		posts = posts.stream()
				.filter(post -> post.getStatus().equals(Status.PUBLISHED))
				.collect(Collectors.toList());
		// transform
		List<PostExcerptResponse> postResponses = posts.stream()
				.map(post -> new PostExcerptResponse(
						post.getAuthor().getId(),
						post.getAuthor().getUsername(),
						post.getId(),
						post.getTitle(),
						makeExcerpt(post.getBody()),
						post.getSlug(),
						post.getThread().getId(),
						post.getThread().getName(),
						post.getCreatedAt(),
						post.getLastUpdatedAt(),
						post.getPublishedAt(),
						post.getStatus()))
				.collect(Collectors.toList());
		return new PostListResponse(postResponses);
	}

	@Override
	public PostListResponse getAllPosts() {
		List<Post> posts = postRepository.findAll();
		return getPostListResponseFromPosts(posts);
	}

	@Override
	public CreatePostResponse createPost(
			CreatePostRequest request,
			String username) {
		// errors
		User author = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundByUsernameException(username));
		if (postRepository.existsByAuthorIdAndTitle(author.getId(), request.getTitle())) {
			throw new PostTitleAlreadyTakenException(request.getTitle());
		}
		Thread thread = threadRepository.findById(request.getThreadId())
				.orElseThrow(() -> new ThreadNotFoundByIdException(request.getThreadId()));
		// create post
		Post newPost = new Post(
				author,
				thread,
				request.getTitle(),
				request.getBody(),
				makeSlug(request.getTitle()),
				Status.PUBLISHED);
		postRepository.save(newPost);
		// create response
		CreatePostResponse response = new CreatePostResponse(
				author.getId(),
				author.getUsername(),
				newPost.getId(),
				newPost.getTitle(),
				newPost.getBody(),
				newPost.getSlug(),
				thread.getId(),
				thread.getName(),
				newPost.getCreatedAt(),
				newPost.getLastUpdatedAt(),
				newPost.getPublishedAt(),
				newPost.getStatus());
		return response;
	}

	@Override
	public PostResponse getPost(Long id) {
		// errors
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new PostNotFoundByIdException(id));
		// create response
		User author = post.getAuthor();
		Thread thread = post.getThread();
		PostResponse response = new PostResponse(
				author.getId(),
				author.getUsername(),
				post.getId(),
				post.getTitle(),
				post.getBody(),
				post.getSlug(),
				thread.getId(),
				thread.getName(),
				post.getCreatedAt(),
				post.getLastUpdatedAt(),
				post.getPublishedAt(),
				post.getStatus());
		return response;
	}

	@Override
	public UpdatePostResponse updatePost(
			UpdatePostRequest request,
			String authorName,
			Long id) {
		// errors
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new PostNotFoundByIdException(id));
		User author = userRepository.findByUsername(authorName)
				.orElseThrow(() -> new UserNotFoundByUsernameException(authorName));
		if (author.getId() != post.getAuthor().getId()) {
			throw new IsNotAuthorException(authorName);
		}
		// update
		if (request.getThreadId() != null) {
			Long threadId = request.getThreadId();
			Thread thread = threadRepository.findById(threadId)
							.orElseThrow(() -> new ThreadNotFoundByIdException(threadId));
			post.setThread(thread);
		}
		if (request.getPostTitle() != null) {
			post.setTitle(request.getPostTitle());
		}
		if (request.getPostBody() != null) {
			post.setBody(request.getPostBody());
		}
		postRepository.save(post);
		// create response
		Thread thread = post.getThread();
		UpdatePostResponse response = new UpdatePostResponse(
				author.getId(),
				author.getUsername(),
				post.getId(),
				post.getTitle(),
				post.getBody(),
				post.getSlug(),
				thread.getId(),
				thread.getName(),
				post.getCreatedAt(),
				post.getLastUpdatedAt(),
				post.getPublishedAt(),
				post.getStatus());
		return response;
	}


	@Override
	public void deletePost(String username, Long id) {
		// errors
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundByUsernameException(username));
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new PostNotFoundByIdException(id));
		if (user.getId() != post.getAuthor().getId()) {
			throw new IsNotAuthorException(user.getUsername());
		}
		// delete
		postRepository.delete(post);
	}

	@Override
	public PostListResponse getUserPosts(Long id) {
		userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundByIdException(id));
		List<Post> posts = postRepository.findByAuthorId(id);
		return getPostListResponseFromPosts(posts);
	}

	@Override
	public PostListResponse getThreadPosts(Long id) {
		threadRepository.findById(id)
				.orElseThrow(() -> new ThreadNotFoundByIdException(id));
		List<Post> posts = postRepository.findByThreadId(id);
		return getPostListResponseFromPosts(posts);
	}
}

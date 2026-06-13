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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

	@Override
	public PostListResponse getAllPosts() {
		List<Post> posts = postRepository.findAll();
		List<PostExcerptResponse> postResponses = new ArrayList<>();
		for (Post post: posts) {
			PostExcerptResponse postResponse = new PostExcerptResponse();
			postResponse.setAuthorId(post.getAuthor().getId());
			postResponse.setAuthorName(post.getAuthor().getUsername());
			postResponse.setPostId(post.getId());
			postResponse.setPostTitle(post.getTitle());
			postResponse.setPostExcerpt(makeExcerpt(post.getBody()));
			postResponse.setSlug(post.getSlug());
			postResponse.setThreadId(post.getThread().getId());
			postResponse.setThreadName(post.getThread().getName());
			postResponse.setCreatedAt(post.getCreatedAt());
			postResponse.setLastUpdatedAt(post.getLastUpdatedAt());
			postResponse.setPublishedAt(post.getPublishedAt());
			postResponse.setStatus(post.getStatus());
			postResponses.add(postResponse);
		}
		// create response
		PostListResponse response = new PostListResponse();
		response.setPosts(postResponses);
		return response;
	}

	@Override
	public CreatePostResponse createPost(
			CreatePostRequest request,
			String username) {
		// errors
		User author = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundByUsernameException(username));
		List<Post> authorsPosts = postRepository.findByAuthorId(author.getId());
		for (Post post: authorsPosts) {
			if (post.getTitle().equals(request.getTitle())) {
				throw new PostTitleAlreadyTakenException(request.getTitle());
			}
		}
		// create post
		Post newPost = new Post();
		newPost.setAuthor(author);
		Thread thread = threadRepository.findById(request.getThreadId())
				.orElseThrow(() -> new ThreadNotFoundByIdException(request.getThreadId()));
		newPost.setThread(thread);
		newPost.setTitle(request.getTitle());
		newPost.setBody(request.getBody());
		String slug = makeSlug(request.getTitle());
		newPost.setSlug(slug);
		newPost.setStatus(Status.PUBLISHED);
		postRepository.save(newPost);
		// create response
		CreatePostResponse response = new CreatePostResponse();
		response.setAuthorId(author.getId());
		response.setAuthorName(author.getUsername());
		response.setPostId(newPost.getId());
		response.setPostBody(newPost.getBody());
		response.setSlug(newPost.getSlug());
		response.setThreadId(thread.getId());
		response.setThreadName(thread.getName());
		response.setCreatedAt(newPost.getCreatedAt());
		response.setLastUpdatedAt(newPost.getLastUpdatedAt());
		response.setPublishedAt(newPost.getPublishedAt());
		return response;
	}

	@Override
	public PostResponse getPost(Long id) {
		// errors
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new PostNotFoundByIdException(id));
		// create response
		PostResponse response = new PostResponse();
		User author = post.getAuthor();
		response.setAuthorId(author.getId());
		response.setAuthorName(author.getUsername());
		response.setPostId(post.getId());
		response.setPostTitle(post.getTitle());
		response.setPostBody(post.getBody());
		response.setSlug(post.getSlug());
		Thread thread = post.getThread();
		response.setThreadId(thread.getId());
		response.setThreadName(thread.getName());
		response.setCreatedAt(post.getCreatedAt());
		response.setLastUpdatedAt(post.getLastUpdatedAt());
		response.setPublishedAt(post.getPublishedAt());
		response.setStatus(post.getStatus());
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
		UpdatePostResponse response = new UpdatePostResponse();
		response.setAuthorId(author.getId());
		response.setAuthorName(author.getUsername());
		response.setPostId(post.getId());
		response.setPostTitle(post.getTitle());
		response.setPostBody(post.getBody());
		response.setSlug(post.getSlug());
		Thread thread = post.getThread();
		response.setThreadId(thread.getId());
		response.setThreadName(thread.getName());
		response.setCreatedAt(post.getCreatedAt());
		response.setLastUpdatedAt(post.getLastUpdatedAt());
		response.setPublishedAt(post.getPublishedAt());
		response.setStatus(post.getStatus());
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
		List<Post> posts = postRepository.findByAuthorId(id);
		List<PostExcerptResponse> postResponses = new ArrayList<>();
		for (Post post: posts) {
			PostExcerptResponse postResponse = new PostExcerptResponse();
			postResponse.setAuthorId(post.getAuthor().getId());
			postResponse.setAuthorName(post.getAuthor().getUsername());
			postResponse.setPostId(post.getId());
			postResponse.setPostTitle(post.getTitle());
			postResponse.setPostExcerpt(makeExcerpt(post.getBody()));
			postResponse.setSlug(post.getSlug());
			postResponse.setThreadId(post.getThread().getId());
			postResponse.setThreadName(post.getThread().getName());
			postResponse.setCreatedAt(post.getCreatedAt());
			postResponse.setLastUpdatedAt(post.getLastUpdatedAt());
			postResponse.setPublishedAt(post.getPublishedAt());
			postResponse.setStatus(post.getStatus());
			postResponses.add(postResponse);
		}
		// create response
		PostListResponse response = new PostListResponse();
		response.setPosts(postResponses);
		return response;
	}

	@Override
	public PostListResponse getThreadPosts(Long id) {
		List<Post> posts = postRepository.findByThreadId(id);
		List<PostExcerptResponse> postResponses = new ArrayList<>();
		for (Post post: posts) {
			PostExcerptResponse postResponse = new PostExcerptResponse();
			postResponse.setAuthorId(post.getAuthor().getId());
			postResponse.setAuthorName(post.getAuthor().getUsername());
			postResponse.setPostId(post.getId());
			postResponse.setPostTitle(post.getTitle());
			postResponse.setPostExcerpt(makeExcerpt(post.getBody()));
			postResponse.setSlug(post.getSlug());
			postResponse.setThreadId(post.getThread().getId());
			postResponse.setThreadName(post.getThread().getName());
			postResponse.setCreatedAt(post.getCreatedAt());
			postResponse.setLastUpdatedAt(post.getLastUpdatedAt());
			postResponse.setPublishedAt(post.getPublishedAt());
			postResponse.setStatus(post.getStatus());
			postResponses.add(postResponse);
		}
		// create response
		PostListResponse response = new PostListResponse();
		response.setPosts(postResponses);
		return response;
	}
}

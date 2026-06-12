package com.andrew.blog.services;

import com.andrew.blog.dtos.errors.*;
import com.andrew.blog.dtos.requests.CreateCommentRequest;
import com.andrew.blog.dtos.requests.UpdateCommentRequest;
import com.andrew.blog.dtos.responses.CommentListResponse;
import com.andrew.blog.dtos.responses.CommentResponse;
import com.andrew.blog.dtos.responses.CreateCommentResponse;
import com.andrew.blog.dtos.responses.UpdateCommentResponse;
import com.andrew.blog.entities.Comment;
import com.andrew.blog.entities.Post;
import com.andrew.blog.entities.User;
import com.andrew.blog.repositories.CommentRepository;
import com.andrew.blog.repositories.PostRepository;
import com.andrew.blog.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;

	public CommentServiceImpl(
			UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
	}

	@Override
	public CommentListResponse getPostComments(Long id) {
		if (!postRepository.existsById(id)) {
			throw new PostNotFoundByIdException(id);
		}
		List<Comment> comments = commentRepository.findByPostId(id);
		List<CommentResponse> commentResponses = new ArrayList<>();
		for (Comment comment: comments) {
			CommentResponse el = new CommentResponse();
			User author = userRepository.findById(comment.getId())
					.orElseThrow(() -> new UserNotFoundByIdException(id));
			el.setAuthorId(author.getId());
			el.setAuthorName(author.getUsername());
			Long postId = comment.getPost().getId();
			Post post = postRepository.findById(postId)
					.orElseThrow(() -> new PostNotFoundByIdException(postId));
			el.setPostId(post.getId());
			el.setPostTitle(post.getTitle());
			el.setCommentId(comment.getId());
			el.setCommentBody(comment.getBody());
			if (comment.getParent() != null) {
				el.setParentCommentId(comment.getParent().getId());
			} else {
				el.setParentCommentId(null);
			}
			el.setCreatedAt(comment.getCreatedAt());
			el.setLastUpdatedAt(comment.getLastUpdatedAt());
			commentResponses.add(el);
		}
		CommentListResponse response = new CommentListResponse();
		response.setComments(commentResponses);
		return response;
	}

	@Override
	public CreateCommentResponse createComment(
			CreateCommentRequest request,
			String username,
			Long id) {
		// errors
		User author = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundByUsernameException(username));
		// create new comment
		Comment newComment = new Comment();
		newComment.setUser(author);
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new PostNotFoundByIdException(id));
		newComment.setPost(post);
		if (request.getParentId() != null) {
			Comment parent = commentRepository.findById(request.getParentId())
					.orElseThrow(() -> new CommentNotFoundByIdException(id));
			newComment.setParent(parent);
		} else {
			newComment.setParent(null);
		}
		newComment.setBody(request.getCommentBody());
		newComment.setIsDeleted(false);
		commentRepository.save(newComment);
		// create response
		CreateCommentResponse response = new CreateCommentResponse();
		response.setAuthorId(author.getId());
		response.setAuthorName(author.getUsername());
		response.setPostId(post.getId());
		response.setPostTitle(post.getTitle());
		response.setCommentId(newComment.getId());
		response.setCommentBody(newComment.getBody());
		response.setParentCommentId(request.getParentId());
		response.setCreatedAt(newComment.getCreatedAt());
		response.setLastUpdatedAt(newComment.getLastUpdatedAt());
		return response;
	}

	@Override
	public UpdateCommentResponse updateComment(
			UpdateCommentRequest request,
			String username,
			Long id) {
		// errors
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new CommentNotFoundByIdException(id));
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundByUsernameException(username));
		if (user.getId() != comment.getUser().getId()) {
			throw new IsNotAuthorException(username);
		}
		// update
		if (request.getCommentBody() != null) {
			comment.setBody(request.getCommentBody());
		}
		commentRepository.save(comment);
		// create response
		UpdateCommentResponse response = new UpdateCommentResponse();
		response.setAuthorId(user.getId());
		response.setAuthorName(user.getUsername());
		Long postId = comment.getPost().getId();
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new PostNotFoundByIdException(postId));
		response.setPostId(post.getId());
		response.setPostTitle(post.getTitle());
		response.setCommentId(comment.getId());
		response.setCommentBody(comment.getBody());
		response.setParentCommentId(comment.getParent().getId());
		response.setCreatedAt(comment.getCreatedAt());
		response.setLastUpdatedAt(comment.getLastUpdatedAt());
		return response;
	}

	@Override
	public void deleteComment(Long id, String username) {
		// errors
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new CommentNotFoundByIdException(id));
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundByUsernameException(username));
		if (user.getId() != comment.getUser().getId()) {
			throw new IsNotAuthorException(username);
		}
		// delete
		commentRepository.delete(comment);
	}
}

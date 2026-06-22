package com.andrew.blog.services;

import com.andrew.blog.dtos.errors.CommentNotFoundByIdException;
import com.andrew.blog.dtos.errors.IsNotAuthorException;
import com.andrew.blog.dtos.errors.PostNotFoundByIdException;
import com.andrew.blog.dtos.errors.UserNotFoundByUsernameException;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
		// resources
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new PostNotFoundByIdException(id));
		List<Comment> comments = commentRepository.findByPostId(id);
		// response
		List<CommentResponse> commentResponses = comments.stream()
				.map(comment -> new CommentResponse(
							comment.getUser().getId(),
							comment.getUser().getUsername(),
							id,
							post.getTitle(),
							comment.getId(),
							comment.getBody(),
							comment.getParent() == null ?
								null : comment.getParent().getId(),
							comment.getCreatedAt(),
							comment.getLastUpdatedAt()))
				.collect(Collectors.toList());
		return new CommentListResponse(commentResponses);
	}

	@Override
	public CommentResponse getComment(Long id) {
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new CommentNotFoundByIdException(id));
		CommentResponse response = new CommentResponse(
				comment.getUser().getId(),
				comment.getUser().getUsername(),
				comment.getPost().getId(),
				comment.getPost().getTitle(),
				comment.getId(),
				comment.getBody(),
				comment.getParent() == null ?
						null : comment.getParent().getId(),
				comment.getCreatedAt(),
				comment.getLastUpdatedAt());
		return response;
	}

	@Override
	@Transactional
	public CreateCommentResponse createComment(
			CreateCommentRequest request,
			String username,
			Long id) {
		// resources
		User author = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundByUsernameException(username));
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new PostNotFoundByIdException(id));
		Comment parent;
		Long parentId;
		if (request.getParentId() == null) {
			parent = null;
			parentId = null;
		} else {
			parent = commentRepository.findById(request.getParentId())
					.orElseThrow(() -> new CommentNotFoundByIdException(id));
			parentId = parent.getId();
		}
		// create
		Comment newComment = new Comment(
				author,
				post,
				parent,
				request.getCommentBody());
		commentRepository.save(newComment);
		// response
		CreateCommentResponse response = new CreateCommentResponse(
				author.getId(),
				author.getUsername(),
				post.getId(),
				post.getTitle(),
				newComment.getId(),
				newComment.getBody(),
				parentId,
				newComment.getCreatedAt(),
				newComment.getLastUpdatedAt());
		return response;
	}

	@Override
	@Transactional
	public UpdateCommentResponse updateComment(
			UpdateCommentRequest request,
			String username,
			Long id) {
		// resources
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new CommentNotFoundByIdException(id));
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundByUsernameException(username));
		Long postId = comment.getPost().getId();
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new PostNotFoundByIdException(postId));
		if (!user.getId().equals(comment.getUser().getId())) {
			throw new IsNotAuthorException(username);
		}
		// update
		if (request.getCommentBody() != null) {
			comment.setBody(request.getCommentBody());
		}
		if (request.getParentId() != null) {
			Comment parent = commentRepository.findById(request.getParentId())
							.orElseThrow(() -> new CommentNotFoundByIdException(request.getParentId()));
			comment.setParent(parent);
		}
		commentRepository.save(comment);
		// response
		Long parentId = null;
		if (comment.getParent() != null) {
			parentId = comment.getParent().getId();
		}
		UpdateCommentResponse response = new UpdateCommentResponse(
				user.getId(),
				user.getUsername(),
				post.getId(),
				post.getTitle(),
				comment.getId(),
				comment.getBody(),
				parentId,
				comment.getCreatedAt(),
				comment.getLastUpdatedAt());
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

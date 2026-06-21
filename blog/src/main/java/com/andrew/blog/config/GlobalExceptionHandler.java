package com.andrew.blog.config;

import com.andrew.blog.dtos.errors.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UsernameAlreadyTakenException.class)
	public ResponseEntity<BasicErrorResponse> handleUsernameTaken(UsernameAlreadyTakenException e) {
		BasicErrorResponse response = new BasicErrorResponse();
		response.setCode("USERNAME_TAKEN");
		response.setMessage(e.getMessage());
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body(response);
	}

	@ExceptionHandler(ThreadNameAlreadyTakenException.class)
	public ResponseEntity<BasicErrorResponse> handleThreadNameTaken(ThreadNameAlreadyTakenException e) {
		BasicErrorResponse response = new BasicErrorResponse();
		response.setCode("THREAD_NAME_TAKEN");
		response.setMessage(e.getMessage());
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body(response);
	}

	@ExceptionHandler(EmailAlreadyTakenException.class)
	public ResponseEntity<BasicErrorResponse> handleEmailTaken(EmailAlreadyTakenException e) {
		BasicErrorResponse response = new BasicErrorResponse();
		response.setCode("EMAIL_TAKEN");
		response.setMessage(e.getMessage());
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body(response);
	}

	@ExceptionHandler(PostTitleAlreadyTakenException.class)
	public ResponseEntity<BasicErrorResponse> handlePostTitleTaken(PostTitleAlreadyTakenException e) {
		BasicErrorResponse response = new BasicErrorResponse();
		response.setCode("POST_TITLE_TAKEN");
		response.setMessage(e.getMessage());
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body(response);
	}

	@ExceptionHandler(MascotNameAlreadyTakenException.class)
	public ResponseEntity<BasicErrorResponse> handleMascotNameTaken(MascotNameAlreadyTakenException e) {
		BasicErrorResponse response = new BasicErrorResponse();
		response.setCode("MASCOT_NAME_TAKEN");
		response.setMessage(e.getMessage());
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body(response);
	}

	@ExceptionHandler(MascotNotFoundByIdException.class)
	public ResponseEntity<BasicErrorResponse> handleMascotNotFound(MascotNotFoundByIdException e) {
		BasicErrorResponse response = new BasicErrorResponse();
		response.setCode("MASCOT_NOT_FOUND");
		response.setMessage(e.getMessage());
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(response);
	}

	@ExceptionHandler(CommentNotFoundByIdException.class)
	public ResponseEntity<BasicErrorResponse> handleCommentNotFound(CommentNotFoundByIdException e) {
		BasicErrorResponse response = new BasicErrorResponse();
		response.setCode("COMMENT_NOT_FOUND");
		response.setMessage(e.getMessage());
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(response);
	}

	@ExceptionHandler(UserNotFoundByIdException.class)
	public ResponseEntity<BasicErrorResponse> handleUserByIdNotFound(UserNotFoundByIdException e) {
		BasicErrorResponse response = new BasicErrorResponse();
		response.setCode("USER_NOT_FOUND");
		response.setMessage(e.getMessage());
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(response);
	}

	@ExceptionHandler(UserNotFoundByUsernameException.class)
	public ResponseEntity<BasicErrorResponse> handleUserByUsernameNotFound(UserNotFoundByUsernameException e) {
		BasicErrorResponse response = new BasicErrorResponse();
		response.setCode("USER_NOT_FOUND");
		response.setMessage(e.getMessage());
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(response);
	}

	@ExceptionHandler(ThreadNotFoundByIdException.class)
	public ResponseEntity<BasicErrorResponse> handleThreadByIdNotFound(ThreadNotFoundByIdException e) {
		BasicErrorResponse response = new BasicErrorResponse();
		response.setCode("THREAD_NOT_FOUND");
		response.setMessage(e.getMessage());
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(response);
	}

	@ExceptionHandler(PostNotFoundByIdException.class)
	public ResponseEntity<BasicErrorResponse> handlePostByIdNotFound(PostNotFoundByIdException e) {
		BasicErrorResponse response = new BasicErrorResponse();
		response.setCode("POST_NOT_FOUND");
		response.setMessage(e.getMessage());
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(response);
	}

	@ExceptionHandler(UsernameOrEmailNotFoundException.class)
	public ResponseEntity<BasicErrorResponse> handleUserByUsernameOrEmailNotFound(UsernameOrEmailNotFoundException e) {
		BasicErrorResponse response = new BasicErrorResponse();
		response.setCode("USER_NOT_FOUND");
		response.setMessage(e.getMessage());
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(response);
	}

	@ExceptionHandler(RefreshDoesNotExistException.class)
	public ResponseEntity<BasicErrorResponse> handleRefreshDoesNotExist(RefreshDoesNotExistException e) {
		BasicErrorResponse response = new BasicErrorResponse();
		response.setCode("REFRESH_INVALID");
		response.setMessage(e.getMessage());
		return ResponseEntity
				.status(HttpStatus.FORBIDDEN)
				.body(response);
	}

	@ExceptionHandler(IsNotAdminException.class)
	public ResponseEntity<BasicErrorResponse> handleIsNotAdmin(IsNotAdminException e) {
		BasicErrorResponse response = new BasicErrorResponse();
		response.setCode("NOT_AUTHORIZED");
		response.setMessage(e.getMessage());
		return ResponseEntity
				.status(HttpStatus.FORBIDDEN)
				.body(response);
	}

	@ExceptionHandler(IsNotAuthorException.class)
	public ResponseEntity<BasicErrorResponse> handleIsNotAuthor(IsNotAuthorException e) {
		BasicErrorResponse response = new BasicErrorResponse();
		response.setCode("NOT_AUTHORIZED");
		response.setMessage(e.getMessage());
		return ResponseEntity
				.status(HttpStatus.FORBIDDEN)
				.body(response);
	}
}

package com.sha.insta.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sha.insta.payload.CommentDto;
import com.sha.insta.service.impl.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
	
	private CommentService commentService;

	public CommentController(CommentService commentService) {
		super();
		this.commentService = commentService;
	}
	
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable("postId") long postId, @Valid @RequestBody CommentDto commentDto){
		return new ResponseEntity<> (commentService.createComment(postId, commentDto), HttpStatus.CREATED);
	}
	@GetMapping("/posts/{postId}/comments")
	public List<CommentDto> getCommentsByPostId(@PathVariable(value="postId") Long postId){
		return commentService.getCommentsByPostId(postId);
	}
	
	@GetMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> getCommentsByPostId(@PathVariable(value="postId") Long postId,
			@PathVariable(value="id") Long commentId){
		
		CommentDto commentDto = commentService.getCommentById(postId, commentId);
		return new ResponseEntity<>(commentDto, HttpStatus.OK);
	}
	
	@PutMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable(value="postId") Long postId,
			@PathVariable("id") Long commentId,@Valid @RequestBody CommentDto commentRequest){
		
		CommentDto commentDtoRes = commentService.updateComment(postId, commentId, commentRequest);
		
		return new ResponseEntity<>(commentDtoRes, HttpStatus.OK);
	}
	
	@DeleteMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<String> deleteComment(@PathVariable(value="postId") Long postId,
			@PathVariable("id") Long commentId){
		
		commentService.deleteComment(postId, commentId);
		
		return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
	}

}

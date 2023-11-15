package com.sha.insta.service;

import java.util.List;

import com.sha.insta.payload.CommentDto;

public interface ICommentService {
	
	CommentDto createComment(long postId, CommentDto commentDto);
	
	List<CommentDto> getCommentsByPostId(long postId);
	
	CommentDto getCommentById(Long postId, Long commentId);
	
	CommentDto updateComment(Long postId, long commentId,CommentDto commentRequest);
	
	void deleteComment(Long postId, Long commentId);
	
	

}

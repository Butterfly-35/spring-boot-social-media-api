package com.sha.insta.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.Comments;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sha.insta.entity.Comment;
import com.sha.insta.entity.Post;
import com.sha.insta.exception.BlogAPIException;
import com.sha.insta.exception.ResourceNotFoundException;
import com.sha.insta.payload.CommentDto;
import com.sha.insta.repository.CommentRepository;
import com.sha.insta.repository.PostRepository;
import com.sha.insta.service.ICommentService;

@Service
public class CommentService implements ICommentService{
	
	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private ModelMapper mapper;
	
	
	

	public CommentService(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
		super();
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.mapper = mapper;
	}

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		
		Comment comment = mapToEntity(commentDto);
		// TODO Auto-generated method stub
		
		
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		
		comment.setPost(post);
		
		Comment newComment = commentRepository.save(comment);
		
		
		return mapToDTO(newComment);
	}

	private CommentDto mapToDTO(Comment newComment) {
		// TODO Auto-generated method stub
		
		CommentDto commentDto = mapper.map(newComment, CommentDto.class);
		return commentDto;
	}

	private Comment mapToEntity(CommentDto commentDto) {
		// TODO Auto-generated method stub
		
		Comment comment = mapper.map(commentDto, Comment.class);
		return comment;
	}

	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {
		// TODO Auto-generated method stub
		
		
		List<Comment> comments = commentRepository.findByPostId(postId);
		
		return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDto getCommentById(Long postId, Long commentId) {
		// TODO Auto-generated method stub
		
		Post post = postRepository.findById(postId).orElseThrow(
				() -> new ResourceNotFoundException("Post", "id", postId));
		
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> 
		new ResourceNotFoundException("Comment", "id", commentId));
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comments does not belong to post");
		}
		return mapToDTO(comment);
	}

	@Override
	public CommentDto updateComment(Long postId, long commentId, CommentDto commentRequest) {
		// TODO Auto-generated method stub
		
		Post post = postRepository.findById(postId).orElseThrow(() -> 
		new ResourceNotFoundException("Post", "id", postId));
		
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> 
		new ResourceNotFoundException("Comment", "id", commentId));
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comments does not belong to post");
		}
		
		comment.setName(commentRequest.getName());
		comment.setEmail(commentRequest.getEmail());
		comment.setBody(commentRequest.getBody());
		
		Comment updatedComment = commentRepository.save(comment);
		
		
		return mapToDTO(updatedComment);
	}

	@Override
	public void deleteComment(Long postId, Long commentId) {
		// TODO Auto-generated method stub
		
		Post post = postRepository.findById(postId).orElseThrow(() -> 
		new ResourceNotFoundException("Post", "id", postId));
		
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> 
		new ResourceNotFoundException("Comment", "id", commentId));
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comments does not belong to post");
		}
		
		
		commentRepository.delete(comment);
		
	
		
	}

}

package com.sha.insta.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sha.insta.payload.PostDTO;

import com.sha.insta.payload.PostResponse;
import com.sha.insta.service.impl.PostService;
import com.sha.insta.utils.AppConstants;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/posts")
@Tag(name=" CRUD REST Api fro social media")
@Slf4j
public class PostController {
	
	private PostService postService;

	public PostController(PostService postService) {
		super();
		this.postService = postService;
	}
	
	@Operation(
			summary = "Create POST REST API",
			description = "Create Post Rest API is used to save into database")
	@ApiResponse( responseCode= "201",
	description = "Http Status 201 Created")
	@SecurityRequirement(
			name="Bear Authentication")
//	@PreAuthorize("hasRole('USER')")
	@PostMapping
	public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO){
		
		log.info(postDTO.toString());
		return new ResponseEntity<PostDTO>(postService.createPost(postDTO), HttpStatus.CREATED);
	}
	
	@Operation( summary = "Get All Posts REST API",
			description = "Get All Posts REST Api is used for fetch all the posts from db")
	@ApiResponse(
			responseCode="200",
			description = "Http Status 200 SUCCESS")
	
	@GetMapping
	public PostResponse getAllPosts(
			
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required=false) int pageSize,
			@RequestParam(value= "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required= false) String sortDir )
			{
		
		return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
		
	}
	
	@Operation(
			summary = "Get Post By Id REST API",
			description = "Get Post By Id REST API is used for signle post from teh database")
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 211 SUCCESS")
	@GetMapping("/{id}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable(name = "id") long id){
		return ResponseEntity.ok(postService.getPostById(id));
	}
	@Operation(
			summary = "update Post REST EndPoint",
			description = "Update Post REST API is used to update a particular post in the database")
	@ApiResponse(responseCode = "200",
	description = "Http Status 200 SUCCESS")
	@SecurityRequirement(
			name = "Bear Authontication")
//	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDto, @PathVariable(name = "id") long id){
		PostDTO postResponse = postService.updatePost(postDto, id);
		
		return new ResponseEntity<>(postResponse, HttpStatus.OK);
	}
	
	@Operation(
			summary = "Delete Post REST EndPoint",
			description = "Delete Post REST API is used to delete a particular post in the database")
	@ApiResponse(responseCode = "200",
	description = "Http Status 200 SUCCESS")
	@SecurityRequirement(
			name = "Bear Authontication")
//	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePost(@PathVariable(name="id") long id){
		postService.deletePostById(id);
		return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
	}
	
	@GetMapping("/category/{id}")
	public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable("id") Long categoryId){
		List<PostDTO> postDtos = postService.getPostsByCategory(categoryId);
		
		return ResponseEntity.ok(postDtos);
	}
	

}

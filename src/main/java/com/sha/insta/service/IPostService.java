package com.sha.insta.service;

import java.util.List;

import com.sha.insta.payload.PostDTO;
import com.sha.insta.payload.PostResponse;

public interface IPostService {
	
	PostDTO createPost(PostDTO postDto);
	
	PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
	
	PostDTO updatePost(PostDTO postDto, long id);
	
	void deletePostById(long id);
	
	List<PostDTO> getPostsByCategory(Long categoryId);
	
	

}

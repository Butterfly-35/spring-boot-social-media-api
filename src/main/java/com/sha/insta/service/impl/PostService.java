package com.sha.insta.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sha.insta.entity.Category;
import com.sha.insta.entity.Post;
import com.sha.insta.exception.ResourceNotFoundException;
import com.sha.insta.payload.PostDTO;
import com.sha.insta.payload.PostResponse;
import com.sha.insta.repository.CategoryRepository;
import com.sha.insta.repository.PostRepository;
import com.sha.insta.service.IPostService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService implements IPostService{
	
	private final PostRepository postRepository;
	private final ModelMapper modelMapper;
	private final CategoryRepository categoryRepository;
	

	@Override
	public PostDTO createPost(PostDTO postDto) {
		// TODO Auto-generated method stub
		
		
		Category category = categoryRepository.findById(postDto.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
		
		Post post = mapToEntity(postDto);
		post.setCategory(category);
		Post newPost = postRepository.save(post);
		
		PostDTO postResponse = mapToDTO(newPost);
		return postResponse;
	}

	private PostDTO mapToDTO(Post post) {
		// TODO Auto-generated method stub
		
		PostDTO postDto = modelMapper.map(post, PostDTO.class);
		return postDto;
	}

	private Post mapToEntity(PostDTO postDto) {
		// TODO Auto-generated method stub
		
		Post post = modelMapper.map(postDto, Post.class);
		return post;
	}

	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
		// TODO Auto-generated method stub
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
			Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Post> posts = postRepository.findAll(pageable);
		
		List<Post> listOfPosts = posts.getContent();
		
		List<PostDTO> content = listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
		
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElement(posts.getTotalElements());
		postResponse.setLast(posts.isLast());
		
		return postResponse;
	}

	@Override
	public PostDTO updatePost(PostDTO postDto, long id) {
		// TODO Auto-generated method stub
		
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		
		Category category = categoryRepository.findById(postDto.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
		
		
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		post.setCategory(category);
		Post updatedPost = postRepository.save(post);
		
		return mapToDTO(updatedPost);
	}

	@Override
	public void deletePostById(long id) {
		// TODO Auto-generated method stub
		
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		postRepository.delete(post);
		
	}

	@Override
	public List<PostDTO> getPostsByCategory(Long categoryId) {
		// TODO Auto-generated method stub
		
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		
		List<Post> posts = postRepository.findByCategoryId(categoryId);
		
		return posts.stream().map((post) -> mapToDTO(post)).collect(Collectors.toList());
	}

	public PostDTO getPostById(long id) {
		// TODO Auto-generated method stub
		
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		
		
		return mapToDTO(post);
		
	}

}

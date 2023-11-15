package com.sha.insta.service;

import java.util.List;

import com.sha.insta.payload.CategoryDto;


public interface ICategoryService {
	
	CategoryDto addCategory(CategoryDto catgoryDto);
	
	CategoryDto getCategory(Long categoryId);
	
	List<CategoryDto> getAllCategories();
	
	CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);
	
	void deleteCategory(Long categoryId);

}

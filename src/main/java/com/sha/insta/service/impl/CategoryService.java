package com.sha.insta.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.sha.insta.entity.Category;
import com.sha.insta.exception.ResourceNotFoundException;
import com.sha.insta.payload.CategoryDto;
import com.sha.insta.repository.CategoryRepository;
import com.sha.insta.service.ICategoryService;

@Service
public class CategoryService implements ICategoryService{
	
	private CategoryRepository categoryRepository;
	private ModelMapper modelMapper;
	
	

	public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper) {
		super();
		this.categoryRepository = categoryRepository;
		this.modelMapper = modelMapper;
	}

	public CategoryDto addCategory(CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		
		Category category = modelMapper.map(categoryDto, Category.class);
		Category savedCategory = categoryRepository.save(category);
		return modelMapper.map(savedCategory, CategoryDto.class);
	}

	public List<CategoryDto> getAllCategories() {
		// TODO Auto-generated method stub
		
		List<Category> categories = categoryRepository.findAll();
		return categories.stream().map((category) -> modelMapper.map(category, CategoryDto.class))
				.collect(Collectors.toList());
	}

	public CategoryDto getCategory(Long categoryId) {
		// TODO Auto-generated method stub
		
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		return modelMapper.map(category, CategoryDto.class);
	}

	public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
		// TODO Auto-generated method stub
		
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
		category.setId(categoryId);
		
		Category updatedCategory = categoryRepository.save(category);
		
		return modelMapper.map(updatedCategory, CategoryDto.class);
	}

	public void deleteCategory(Long categoryId) {
		
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		
		categoryRepository.delete(category);
		
	}

}

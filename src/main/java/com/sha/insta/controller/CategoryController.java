package com.sha.insta.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sha.insta.payload.CategoryDto;
import com.sha.insta.service.impl.CategoryService;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
	
	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		super();
		this.categoryService = categoryService;
	}
	
	@PostMapping
//	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
		
		CategoryDto savedCategory = categoryService.addCategory(categoryDto);
		
		return ResponseEntity.ok(savedCategory);
		
	}
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getCategories(){
		
		return ResponseEntity.ok(categoryService.getAllCategories());
		
	}
	
	@GetMapping("{id}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") Long categoryId){
		
		CategoryDto categoryDto = categoryService.getCategory(categoryId);
		
		return ResponseEntity.ok(categoryDto);
		
	}
	
	@PutMapping("{id}")
//	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
			@PathVariable("id") Long categoryId){
		
		return ResponseEntity.ok(categoryService.updateCategory(categoryDto, categoryId));
		
	}
	
	@DeleteMapping("{id}")
//	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteCategory(
			@PathVariable("id") Long categoryId){
		
		categoryService.deleteCategory(categoryId);
		
		return ResponseEntity.ok("Category deleted successfully!");
		
	}
			
	

}

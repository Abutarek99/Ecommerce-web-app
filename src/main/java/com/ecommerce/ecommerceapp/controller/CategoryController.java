package com.ecommerce.ecommerceapp.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.ecommerceapp.apimodel.ApiResponse;
import com.ecommerce.ecommerceapp.apimodel.CategoryDto;
import com.ecommerce.ecommerceapp.datamodel.Category;
import com.ecommerce.ecommerceapp.exception.AlreadyExistsException;
import com.ecommerce.ecommerceapp.exception.ResourceNotFoundException;
import com.ecommerce.ecommerceapp.service.CategorySerivce;

@RestController
@RequestMapping("/categories")
public class CategoryController {
	@Autowired
	private CategorySerivce categoryService;
	
	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<ApiResponse> addCategory(@RequestBody CategoryDto newCategory){
		try {
			CategoryDto desiredCategory = categoryService.addCategory(newCategory);
			return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Category added successfully", desiredCategory), HttpStatus.OK);
		}
		catch(AlreadyExistsException ex) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, ex.getMessage()), HttpStatus.CONFLICT);
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, ex.getMessage()));
		}
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<ApiResponse> getCategoryById(@PathVariable long id) {
		try {
			Category category = categoryService.getCategoryById(id);
			CategoryDto desiredCategory = CategoryDto.fromEntityToDto(category);
			return ResponseEntity.ok(new ApiResponse(true, "Category found successfully", desiredCategory));
		}
		catch( ResourceNotFoundException ex) {
			 return new ResponseEntity<ApiResponse>(new ApiResponse(false, ex.getMessage()), HttpStatus.NOT_FOUND);
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, ex.getMessage()));
		}
	}
	
	@PutMapping("update/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<ApiResponse> updateExisitngCategory(@RequestBody CategoryDto categoryDetails, @PathVariable long id){
		try {
			CategoryDto desiredCategory = categoryService.updateExisitingCategoryById(categoryDetails, id);
			return ResponseEntity.ok(new ApiResponse(true, "Category updated successfully", desiredCategory));
		}
		catch( ResourceNotFoundException ex) {
			 return new ResponseEntity<ApiResponse>(new ApiResponse(false, ex.getMessage()), HttpStatus.NOT_FOUND);
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, ex.getMessage()));
		}
    }
	
	@GetMapping("/get-all")
	public ResponseEntity<ApiResponse> getAllCategories(){
		try {
			List<Category> categories = categoryService.getAllCategories();
			if(categories.isEmpty())
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, 
						"No categories were found in the system. Please ensure the database is populated"));
			List<CategoryDto> allCategories = CategoryDto.fromEntitylistToDtolist(categories);
			return ResponseEntity.ok(new ApiResponse(true, "Successfully retrieved all categories", allCategories));
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, ex.getMessage()));
		}
	}
	
	@DeleteMapping("delete/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable long id){
		try {
			categoryService.deleteCategoryById(id);
			return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Category deleted successfully"), HttpStatus.OK);
		}
		catch( ResourceNotFoundException ex) {
			 return new ResponseEntity<ApiResponse>(new ApiResponse(false, ex.getMessage()), HttpStatus.NOT_FOUND);
	    }
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, ex.getMessage()));
		}
	}
}

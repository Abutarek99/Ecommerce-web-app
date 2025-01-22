package com.ecommerce.ecommerceapp.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecommerce.ecommerceapp.apimodel.CategoryDto;
import com.ecommerce.ecommerceapp.datamodel.Category;
import com.ecommerce.ecommerceapp.exception.AlreadyExistsException;
import com.ecommerce.ecommerceapp.exception.ResourceNotFoundException;
import com.ecommerce.ecommerceapp.repository.CategoryRepository;

@Service
public class CategorySerivce {
	@Autowired
	private CategoryRepository categoryRepository;
	
	//ADD CATEGORY
	public CategoryDto addCategory(CategoryDto categoryDetails) {
		Category newCategory = Optional.of(categoryDetails)
		        .filter(category -> !categoryRepository.existsByname(categoryDetails.getName()))
		        .map(categ ->{
		        	return new Category(categoryDetails.getName());
		        })
		        .orElseThrow(() -> new AlreadyExistsException("The category" + 
		                           categoryDetails.getName() + " already exists, Please verify and try again "));
		categoryRepository.save(newCategory);
		return CategoryDto.fromEntityToDto(newCategory);
	}
	
	//GET CATEGORY BY ID
	public Category getCategoryById(long id) {
		return categoryRepository.findById(id).
				orElseThrow(()-> new ResourceNotFoundException("The specified category was not found in the system"));
	}
	
	//GET ALL CATEGORIES
	public List<Category> getAllCategories(){
	   return categoryRepository.findAll();
	}
	
	//UPDATE EXISTING CATEGORY
	public CategoryDto updateExisitingCategoryById( CategoryDto newCategory, long id) {
		Optional<Category> category = categoryRepository.findById(id);
		if(category.isEmpty())
			throw new ResourceNotFoundException("The category you are trying to update was not found");
		else {
			Category existingCategory = category.get();
			existingCategory.setName(newCategory.getName());
			return CategoryDto.fromEntityToDto(categoryRepository.save(existingCategory));
		}
	}
	
	//DELETE CATEGORY
	public void deleteCategoryById(long id) {
		categoryRepository.findById(id).ifPresentOrElse(categoryRepository :: delete,
				()-> { throw new ResourceNotFoundException("The category you are trying to delete was not found");});
	}
}

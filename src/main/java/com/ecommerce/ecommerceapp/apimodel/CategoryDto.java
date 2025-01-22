package com.ecommerce.ecommerceapp.apimodel;

import java.util.ArrayList;
import java.util.List;
import com.ecommerce.ecommerceapp.datamodel.Category;

public class CategoryDto {
	private long id;
	private String name;
	
	public CategoryDto() {
		super();
	}

	
	public CategoryDto(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public CategoryDto(String name) {
		super();
		this.name = name;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	//FROM ENTITY TO DTO
	public static CategoryDto fromEntityToDto(Category category) {
		return new CategoryDto(category.getId(), category.getName());
	}
	
	public static List<CategoryDto> fromEntitylistToDtolist(List<Category> categoryEntity){
		List<CategoryDto> listCategoryDto = new ArrayList<>();
		for(Category category : categoryEntity) {
			CategoryDto categoryDto = CategoryDto.fromEntityToDto(category);
			listCategoryDto.add(categoryDto);
		}
		return listCategoryDto;
	}
	
	//FROM DTO TO ENTITY
	public Category fromDtoToEntity() {
		return new Category(this.name);
	}
}

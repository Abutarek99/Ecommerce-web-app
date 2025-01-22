package com.ecommerce.ecommerceapp.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecommerce.ecommerceapp.apimodel.ProductDto;
import com.ecommerce.ecommerceapp.datamodel.Category;
import com.ecommerce.ecommerceapp.datamodel.Product;
import com.ecommerce.ecommerceapp.exception.ResourceNotFoundException;
import com.ecommerce.ecommerceapp.repository.CategoryRepository;
import com.ecommerce.ecommerceapp.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	
	//ADD PRODUCT
	public ProductDto addProduct(ProductDto productDetails) {
		Category category = Optional.ofNullable(categoryRepository.findByname(productDetails.getCategory().getName()))
                .orElseGet(() -> {
                	Category newCategory = new Category(productDetails.getCategory().getName());
                    //Category newCategory = productDetails.getCategory().fromDtoToEntity();
                    return  categoryRepository.save(newCategory);
                 });
		
		Product newProduct = productRepository.save(createProduct(productDetails, category));
		return ProductDto.fromEntityToDto(newProduct);
    }
	
	//METHOD TO CREATE A PRODUCT BASED ON ITS CATEGORY
	public Product createProduct(ProductDto productDetails, Category category) {
		return new Product(productDetails.getName(), productDetails.getDescription(), productDetails.getBrand()
				,productDetails.getPrice(), productDetails.getInventory(), category);
	}
	
	//GET PRODUCT BY ID
	public Product getProductById(long id) {
		return productRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("The specified product was not found in the system."));
	}
	
	
	public ProductDto updateExistingProduct(ProductDto productDetails, long id) {
		Optional<Product> product = productRepository.findById(id);
		if(product.isPresent()) {
			Product existingProduct = product.get();
			existingProduct.setName(productDetails.getName());
			existingProduct.setDescription(productDetails.getDescription());
			existingProduct.setBrand(productDetails.getBrand());
			existingProduct.setPrice(productDetails.getPrice());
			existingProduct.setInventory(productDetails.getInventory());
			Category categoryEntity = categoryRepository.findByname(productDetails.getCategory().getName());
			Category myCategory = Optional.of(categoryEntity).filter(category -> 
			         categoryRepository.existsByname(productDetails.getCategory().getName()))
			                 .orElseThrow(()-> new ResourceNotFoundException("The category you are trying to update "
			               		+ "does not exist in the system."));
			existingProduct.setCategory(myCategory);
			Product updatedProduct = productRepository.save(existingProduct);
			return ProductDto.fromEntityToDto(updatedProduct);
		} 
		else
			throw new ResourceNotFoundException("The product you are trying to update does not exist in the system");
	}
	//GET ALL PRODUCTS
	public List<Product> getAllProducts(){
		return  productRepository.findAll();
	}

	//DELETE PRODUCT BY ID
	public void deleteProductById(long id) {
		productRepository.findById(id).ifPresentOrElse(productRepository::delete,
				()-> {throw new ResourceNotFoundException("The product you are trying to delete does not exist in the system");});
	}
	//GET PRODUCTS BY CATEGORY
	public List<Product> getProductsByCategoryName(String categoryName) {
		return productRepository.findByCategoryName(categoryName);
	}		
	
	//GET PRODUCTS BY BRAND
	public List<Product> getProductsByBrand(String brand){
		return productRepository.findBybrand(brand);
	}
	
	//GET PRODUCTS BY NAME AND BRAND
	public List<Product> getProductsByCategorynameAndBrand(String categoryName, String brand){
		return productRepository.findByCategoryNameAndBrand(categoryName, brand);
	}
	
	//COUNT PRODUCTS BY BRAND AND NAME
	public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

}

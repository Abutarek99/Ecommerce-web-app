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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.ecommerceapp.apimodel.ApiResponse;
import com.ecommerce.ecommerceapp.apimodel.ProductDto;
import com.ecommerce.ecommerceapp.datamodel.Product;
import com.ecommerce.ecommerceapp.exception.ResourceNotFoundException;
import com.ecommerce.ecommerceapp.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductDto newProduct){
		
		try {
			ProductDto desiredProduct = productService.addProduct(newProduct);
			return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product added successfully", desiredProduct), HttpStatus.CREATED);
		}
		catch(Exception ex) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, ex.getMessage()));
    	}
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<ApiResponse> getProductById(@PathVariable long id) {
		try {
			Product product = productService.getProductById(id);
			ProductDto desiredProduct = ProductDto.fromEntityToDto(product);
			return ResponseEntity.ok(new ApiResponse(true, "The product has been successfully found", desiredProduct));
		}
		catch(ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, ex.getMessage()));
		}
	}
	
	@GetMapping("/get-all")
	public  ResponseEntity<ApiResponse> getAllProducts(){
		try {
			List<Product> allProducts = productService.getAllProducts();
			if(allProducts.isEmpty()) 
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, 
						"No products were found in the system. Please ensure the database is populated"));
			List<ProductDto> desiredProducts = ProductDto.fromEntitylistTodDtolist(allProducts);
    		return ResponseEntity.ok(new ApiResponse(true, "Successfully retrieved all categories", desiredProducts)); 
		}
		catch(Exception ex) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, ex.getMessage()));
    	}
	}
	
	@DeleteMapping("delete/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<ApiResponse> deleteProductById(@PathVariable long id) {
		try {
			productService.deleteProductById(id);
			return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product deleted successfully"), HttpStatus.OK);
		}
		catch(ResourceNotFoundException ex) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, ex.getMessage()), HttpStatus.NOT_FOUND);
		}
		catch(Exception ex) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, ex.getMessage()));
    	}
	}
	
	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<ApiResponse> updateExistingProduct(@RequestBody ProductDto productDetails, @PathVariable long id){
		try {
			ProductDto desiredProduct = productService.updateExistingProduct(productDetails, id);
			return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product updated successfully",desiredProduct ), HttpStatus.OK);
		}
		catch(ResourceNotFoundException ex) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, ex.getMessage()), HttpStatus.NOT_FOUND);
		}
		catch(Exception ex) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, ex.getMessage()));
    	}
	}
	
    @GetMapping("/get/by-category-and-brand")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(@RequestParam String category, @RequestParam String brand){
    	try {
    		List<Product> products = productService.getProductsByCategorynameAndBrand(category, brand);
    		if(products.isEmpty())
    			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, 
    					"No products found for the specified category and brand"));
    		List<ProductDto> desiredProducts = ProductDto.fromEntitylistTodDtolist(products);
    		return ResponseEntity.ok(new ApiResponse(true, "Products successfully retrieved for the specified category and brand" 
    				, desiredProducts));
    	}
    	catch(Exception ex) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, ex.getMessage()));
    	}
    }
    
    @GetMapping("/get/by-brand")
    public ResponseEntity<ApiResponse>getProductsByBrand(@RequestParam String brand) {
    	try {
    	    List<Product> products = productService.getProductsByBrand(brand);
    	    if(products.isEmpty()) 
    	    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, 
    	    			"No products found for the specified brand"));
    	    List<ProductDto> desiredProducts = ProductDto.fromEntitylistTodDtolist(products);
    	    return ResponseEntity.ok(new ApiResponse(true, "Products successfully retrieved for the specified brand", desiredProducts));
    	}
    	catch(Exception ex) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, ex.getMessage()));
    	}
    }
    
    @GetMapping("/get/by-category/{category}")
    public ResponseEntity<ApiResponse> getProductsByCategory(@PathVariable String category){
    	try {
    	    List<Product> products = productService.getProductsByCategoryName(category);
    	    if(products.isEmpty())
    	    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, 
    	    			"Either the category does not exist in the system or no specific products are associated with this category"));
    	    List<ProductDto> desiredProducts = ProductDto.fromEntitylistTodDtolist(products);
    	    return ResponseEntity.ok(new ApiResponse(true, "Products successfully retrieved for the specified category", desiredProducts));
    	}
    	catch(Exception ex) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, ex.getMessage()));
    	}
    }
    
}

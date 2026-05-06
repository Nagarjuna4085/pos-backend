package com.apicalls.pos.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apicalls.pos.entity.Product;
import com.apicalls.pos.service.ProductService;

@RestController
@RequestMapping("api/products")
public class ProductController {
	
	final private ProductService productService;
	
	public ProductController(ProductService productService ) {
		// TODO Auto-generated constructor stub
		this.productService = productService;
	}
	
	@GetMapping("/test")
	public String testApi() {
		
		return productService.test();
		
	}
	@PostMapping("/createProduct")
	public Product createProduct(@RequestBody Product p) {
		productService.createProduct(p);
		return p;
	}
	
	@GetMapping("/")
	public List<Product> getAllProducts(){
		return productService.getProducts();
	}
	
	@GetMapping("/{id}")
	public Product getProductById(@PathVariable Long id) {
		return productService.getProductById(id);
	}
	
	@PutMapping("/{id}")
	public Product updateProductById(@PathVariable Long id, @RequestBody Product p) {
		return productService.updateProductById(id, p);
	}
	
	@DeleteMapping("/{id}")
	public String deleteProduct(@PathVariable Long id) {
		return productService.deleteProductById(id);
	}
	
	@GetMapping("/search")
	public List<Product> searchProducts(@RequestParam String name) {
	    return productService.searchProductByName(name);
	}
	
	@GetMapping("/filter")
	public List<Product> getProductsByPriceRange(@RequestParam Double min, @RequestParam Double max){
		return productService.getProductsByPriceRange(min, max);
		
	}
	
	@GetMapping("/low-stock")
	public List<Product> getLowStock(){
		return productService.getLowStock();
	}
		
    @PutMapping("/{id}/mark-featured")
    public Product markFeatured(@PathVariable Long id) {
        return productService.markFeatured(id);
    }

    @GetMapping("/featured")
    public List<Product> getFeaturedProducts() {
        return productService.getFeaturedProducts();
    }

    @PutMapping("/{id}/deactivate")
    public Product deactivateProduct(@PathVariable Long id) {
        return productService.deativeProduct(id);
    }

    @PutMapping("/{id}/reactivate")
    public Product reactiveProduct(@PathVariable Long id) {
        return productService.reactiveProduct(id);
    }

    @GetMapping("/category/{categoryId}")
    public List<Product> getByCategory(@PathVariable Long categoryId) {
        return productService.getProductsByCategory(categoryId);
    }

    @GetMapping("/recent")
    public List<Product> getRecentProducts() {
        return productService.getRecentProducts();
    }

    @PostMapping("/{id}/images")
    public Product addProductImage(@PathVariable Long id, @RequestBody String imageUrl) {
        // You'll need to implement this logic in your ServiceImpl
        return productService.addImage(id, imageUrl);
    }
	
	
	
	@DeleteMapping("/bulk-delete")
	public String bulkDelete(@RequestBody List<Long> ids) {
	    productService.bulkDelete(ids);
	    return "Deleted " + ids.size() + " products.";
	}
	
	
	
	@PutMapping("/update-quantities")
	public String updateQuantities(@RequestBody Map<Long, Integer> quantityUpdates) {
	    productService.updateQuantities(quantityUpdates);
	    return "Quantities updated successfully for " + quantityUpdates.size() + " products.";
	}
	
	
	
	
	
	


}

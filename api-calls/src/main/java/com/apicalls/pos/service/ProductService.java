package com.apicalls.pos.service;

import java.util.List;
import java.util.Map;

import com.apicalls.pos.entity.Product;

public interface ProductService {
	
	public String test();
	
	public Product createProduct(Product p);
	public List<Product> getProducts();
	public Product getProductById(Long id);
	public Product updateProductById(Long id,Product p);
	public String deleteProductById(Long id);
	public List<Product> searchProductByName(String name);
	List<Product> getProductsByPriceRange(Double min, Double max);
	List<Product> getLowStock();
	void bulkDelete(List<Long> ids);
	Product markFeatured(Long id);
	Product deativeProduct(Long id);
	Product reactiveProduct(Long id);
	List<Product> getProductsByCategory(Long categoryId);
	List<Product> getFeaturedProducts();
	List<Product> getRecentProducts();
	Product addImage(Long id, String imageUrl);
	
	void updateQuantities(Map<Long, Integer> quantityUpdates);

	// 10. Get Top-Selling Products
//	List<Product> getTopSellingProducts();

}

package com.apicalls.pos.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.apicalls.pos.entity.Product;
import com.apicalls.pos.service.ProductService;

@Service
public class ProductImple implements ProductService {
	List<Product> products = new ArrayList<>();

	@Override
	public String test() {
		// TODO Auto-generated method stub
		return "test api is working";
	}

	@Override
	public Product createProduct(Product p) {
		// TODO Auto-generated method stub
		products.add(p);
		return p;
	}

	@Override
	public List<Product> getProducts() {
		// TODO Auto-generated method stub
		return this.products;
	}

	@Override
	public Product getProductById(Long id) {
		// TODO Auto-generated method stub
		return products.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
	}

	@Override
	public Product updateProductById(Long id, Product p) {
		// TODO Auto-generated method stub
		int ind = -1;
		boolean flag = false;
		for (int i = 0; i < products.size(); i++) {
			if (products.get(i).getId() == id) {
				ind = i;
				break;
			}
		}
		if (ind != 1) {
//			p.setId(id); 
			products.set(ind, p);
			return p;
		}
		return null;

	}

	@Override
	public String deleteProductById(Long id) {
		// TODO Auto-generated method stub
		boolean removed = products.removeIf(p -> p.getId().equals(id));
		if (removed) {
			return "produced is deleted";
		}
		return "no product found";
	}

	@Override
	public List<Product> searchProductByName(String name) {
		// TODO Auto-generated method stub
		if (name == null || name.isEmpty()) {
			return products;
		}
		return products.stream()
				.filter(p -> p.getName() != null && p.getName().toLowerCase().contains(name.toLowerCase())).toList();
	}

	@Override
	public List<Product> getProductsByPriceRange(Double min, Double max) {
		return products.stream().filter(p -> p.getPrice() >= min && p.getPrice() <= max).toList();
	}

	@Override
	public List<Product> getLowStock() {
		return products.stream().filter(p -> p.getQuantity() < 10).toList();

	}

	@Override
	public void bulkDelete(List<Long> ids) {
		products.removeIf(p -> ids.contains(p.getId()));
	}



	@Override
	public Product markFeatured(Long id) {
		Product p = getProductById(id);
		if (p != null) {
			p.setIsFeatured(true);

		}
		return p;
	}

	@Override
	public Product deativeProduct(Long id) {
		Product p = getProductById(id);
		if (p != null) {
			p.setIsActive(false);

		}
		return p;
	}

	@Override
	public Product reactiveProduct(Long id) {
		Product p = getProductById(id);
		if (p != null) {

			p.setIsActive(true);
		}
		return p;
	}

	@Override
	public List<Product> getProductsByCategory(Long categoryId) {
		return products.stream().filter(p -> p.getCategoryId().equals(categoryId)).toList();
	}
	
	
	@Override
	public List<Product> getFeaturedProducts() {
	    return products.stream()
	            .filter(p -> Boolean.TRUE.equals(p.getIsFeatured()))
	            .toList();
	}
	
	@Override
	public Product addImage(Long id, String imageUrl) {
	    Product p = getProductById(id);
	    if (p != null) {
	        // If the list is null (which can happen with some JSON parsers), initialize it
	        if (p.getImageUrls() == null) {
	            p.setImageUrls(new ArrayList<>());
	        }
	        
	        // Add the new image URL to the existing list
	        p.getImageUrls().add(imageUrl);
	        return p;
	    }
	    return null; // Product not found
	}

	
	@Override
	public List<Product> getRecentProducts() {
	    return products.stream()
	            .sorted((p1, p2) -> p2.getCreatedDate().compareTo(p1.getCreatedDate()))
	            .limit(5)
	            .toList();
	}
	
	@Override
	public void updateQuantities(Map<Long, Integer> quantityUpdates) {
	    quantityUpdates.forEach((id, newQty) -> {
	        Product p = getProductById(id);
	        if (p != null) {
	            p.setQuantity(newQty);
	        }
	    });
	}
	
	
	
	
	
}

package com.apicalls.pos.entity;

import java.time.LocalDateTime;
import java.util.List;


public class Product {
	   private Long id;
	    private String name;
	    private String description;
	    private Double price;
	    private Integer quantity;
	    private Boolean isFeatured;
	    private Boolean isActive;
	    private Long categoryId;
	    private LocalDateTime createdDate;
	    private List<String> imageUrls; // List to store image URLs
	    private int pusrqy ;//
		public Long getId() {
			return id;
		}

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public Double getPrice() {
			return price;
		}
		public void setPrice(Double price) {
			this.price = price;
		}
		public Integer getQuantity() {
			return quantity;
		}
		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}
		public Boolean getIsFeatured() {
			return isFeatured;
		}
		public void setIsFeatured(Boolean isFeatured) {
			this.isFeatured = isFeatured;
		}
		public Boolean getIsActive() {
			return isActive;
		}
		public void setIsActive(Boolean isActive) {
			this.isActive = isActive;
		}
		public Long getCategoryId() {
			return categoryId;
		}
		public void setCategoryId(Long categoryId) {
			this.categoryId = categoryId;
		}
		public LocalDateTime getCreatedDate() {
			return createdDate;
		}
		public void setCreatedDate(LocalDateTime createdDate) {
			this.createdDate = createdDate;
		}
		public List<String> getImageUrls() {
			return imageUrls;
		}
		public void setImageUrls(List<String> imageUrls) {
			this.imageUrls = imageUrls;
		}
		public int getPusrqy() {
			return pusrqy;
		}
		public void setPusrqy(int pusrqy) {
			this.pusrqy = pusrqy;
		}

}

package com.ecommerce.model;

public class Product
{
 private int productId;
 private int categoryId;
 private String productName;
 private String description;
 private double price;
 private int quantity;
 private String imagePath;
 private String categoryName;
//Getter and Setter Methods
 public int getProductId() {
	return productId;
 }
 public void setProductId(int productId) {
	this.productId = productId;
 }
 public int getCategoryId() {
	return categoryId;
 }
 public void setCategoryId(int categoryId) {
	this.categoryId = categoryId;
 }
 public String getProductName() {
	return productName;
 }
 public void setProductName(String productName) {
	this.productName = productName;
 }
 public String getDescription() {
	return description;
 }
 public void setDescription(String description) {
	this.description = description;
 }
 public double getPrice() {
	return price;
 }
 public void setPrice(double price) {
	this.price = price;
 }
 public int getQuantity() {
	return quantity;
 }
 public void setQuantity(int quantity) {
	this.quantity = quantity;
 }
 public String getImagePath() {
	return imagePath;
 }
 public void setImagePath(String imagePath) {
	this.imagePath = imagePath;
 }
 public String getCategoryName() {
	return categoryName;
 }
 public void setCategoryName(String categoryName) {
	this.categoryName = categoryName;
 }
}

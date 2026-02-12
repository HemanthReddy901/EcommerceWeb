package com.ecommerce.model;

public class OrderItem 
{
 private int orderItemId;
 private int orderId;
 private int productId;
 private double price;
 private int quantity;
 private String productName;
 
 //Getter and Setter Methods
 public int getOrderItemId() {
	return orderItemId;
 }
 public void setOrderItemId(int orderItemId) {
	this.orderItemId = orderItemId;
 }
 public int getOrderId() {
	return orderId;
 }
 public void setOrderId(int orderId) {
	this.orderId = orderId;
 }
 public int getProductId() {
	return productId;
 }
 public void setProductId(int productId) {
	this.productId = productId;
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
 public String getProductName() {
	return productName;
 }
 public void setProductName(String productName) {
	this.productName = productName;
 }
 
}

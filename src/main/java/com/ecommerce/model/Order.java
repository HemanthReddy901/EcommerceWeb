package com.ecommerce.model;

import java.util.Date;
import java.util.List;

public class Order 
{
 private int orderId;
 private int userId;
 private double totalAmount;
 private Date orderDate;
 private String status;
 private String userName;
 private List<OrderItem> orderItems;
//Getter and Setter Methods
 public int getOrderId() {
	return orderId;
 }
 public void setOrderId(int orderId) {
	this.orderId = orderId;
 }
 public int getUserId() {
	return userId;
 }
 public void setUserId(int userId) {
	this.userId = userId;
 }
 public double getTotalAmount() {
	return totalAmount;
 }
 public void setTotalAmount(double totalAmount) {
	this.totalAmount = totalAmount;
 }
 public Date getOrderDate() {
	return orderDate;
 }
 public void setOrderDate(Date orderDate) {
	this.orderDate = orderDate;
 }
 public String getStatus() {
	return status;
 }
 public void setStatus(String status) {
	this.status = status;
 }
 public String getUserName() {
	return userName;
 }
 public void setUserName(String userName) {
	this.userName = userName;
 }
 public List<OrderItem> getOrderItems() {
	return orderItems;
 }
 public void setOrderItems(List<OrderItem> orderItems) {
	this.orderItems = orderItems;
 }
 
 
}

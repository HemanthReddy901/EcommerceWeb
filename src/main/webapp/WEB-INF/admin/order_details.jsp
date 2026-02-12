<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.ecommerce.dao.OrderDAO" %>
<%@ page import="com.ecommerce.model.*" %>
<% 
  if(session.getAttribute("admin") == null)
  {
	  response.sendRedirect(request.getContextPath()+"/user/login.jsp");
	  return;
  }

String orderIdParam = request.getParameter("id");
if(orderIdParam == null)
{
	response.sendRedirect("orders.jsp");
	return;
}

int orderId= Integer.parseInt(orderIdParam);
OrderDAO orderDAO=new OrderDAO();
Order order = orderDAO.getOrderById(orderId);

if(order == null)
{
	response.sendRedirect("orders.jsp?error=Order not found!");
	return;
}
%>
<!DOCTYPE html>
<html>
<head>
<title>Order Details</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
   <jsp:include page="../shared/navbar.jsp"/>
   
   <div class="container">
     <h2>Order Details</h2>
     
     <% if(request.getParameter("success") != null) { %>
     <div class="alert success"><%= request.getParameter("success") %></div>
     <% } %>
     
     <div class="order-details-card">
        <div class="order-info">
          <div class="info-row">
             <span class="info-label">Order ID:</span>
             <span class="info-value">#<%= order.getOrderId() %></span>
          </div>
          <div class="info-row">
              <span class="info-label">Order Date:</span>
              <span class="info-value"> <%= order.getOrderDate() %></span>
              
          </div>
          <div class="info-row">
             <span class="info-label">Status:</span>
             <span class="info-value status <%= order.getStatus() %>"><%= order.getStatus() %></span>
          </div>
          
          <div class="info-row">
             <span class="info-label">Total Amount:</span>
             <span class="info-value">$<%= String.format("%.2f", order.getTotalAmount()) %></span>
          </div>
        </div>
        
        <div class="order-items">
          <h3>Order Items</h3>
          <table>
            <thead>
              <tr>
                 <th>Products</th>
                 <th>Price</th>
                 <th>Quantity</th>
                 <th>Total</th> 
              </tr>
            </thead>
            
            <tbody>
               <% for (OrderItem item: order.getOrderItems()) { %>
               <tr>
                  <td><%= item.getProductName() %></td>
                  <td><%= String.format("%.2f", item.getPrice()) %></td>
                  <td><%= item.getQuantity() %></td>
                  <td><%=String.format("%.2f", item.getPrice() * item.getQuantity()) %></td>
               </tr>
               <% } %>
            </tbody>
          </table>
        </div>
        
        <div class="order-summary">
          <div class="summary-row">
            <span> SubToatal:</span>
            <span>$<%= String.format("%.2f", order.getTotalAmount()) %></span>
          </div>
          <div class="summary-row">
            <span>Shipping:</span>
            <span>Free</span>
          </div>
          <div class="summary-row total">
            <span>Grand Total:</span>
            <span>$<%= String.format("%.2f", order.getTotalAmount()) %></span>
          </div>
        </div>
        
        <div class="back-button">
          <a href="${pageContext.request.contextPath}/order/user" class="btn secondary"> Back to Orders</a>
        </div>
     </div>
   </div>
</body>
</html>
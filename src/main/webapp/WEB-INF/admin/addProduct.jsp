<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="com.ecommerce.dao.CategoryDAO" %>
    <%@ page import="com.ecommerce.model.Category" %>
    <%@ page import="java.util.List" %>
    <% 
    if(session.getAttribute("admin") == null)
    {
    	response.sendRedirect(request.getContextPath()+"/admin-login.jsp");
    	return;
    }
    CategoryDAO categoryDAO=new CategoryDAO();
    List<Category> categories=categoryDAO.getAllCategories();
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Products</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="../shared/navbar.jsp"/>
<div class="container">
<h2>Add New Product</h2>
<% if(request.getParameter("succuss") != null) { %>
<div class="alert success"><%= request.getParameter("succuss") %></div>
<% } %>

<form action="${pageContext.request.contextPath}/product/add" method="post" class="form" enctype="multipart/form-data">
<div class="form-group">
 <label for="categotyId">Category:</label>
 
 <select id="categoryId" name="categoryId" required>
   <option value="">Select Category</option>
   <% for(Category cat:categories) { %>
   <option value="<%= cat.getCategoryId() %>"><%= cat.getCategoryName() %></option>
   <% } %>
 </select>
</div>

  <div class="form-group">
    <label for="productName">Product Name:</label>
    <input type="text" id="productName" name="productName" required/>
  </div>
  
  <div class="form-group">
    <label for="description">Description:</label>
    <input type="text" id="description" name="description" required/>
  </div>
  
  <div class="form-group">
  <label for="price">Price:</label>
  <input type="number" id="price" name="price" required/>
  </div>
  
  <div class="form-group">
  <label for="quantity">Quantity:</label>
  <input type="number" id="quantity" name="quantity" required/>
  </div>
  
  <div class="form-group">
   <label for="image">Product Image:</label>
   <input type="file" id="image" name="image" required/>
  </div>
  <div class="form-group">
   <button type="submit" class="btn">Add Product</button>
   <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn secondary">Cancel</a>
  </div>
</form>
</div>

</body>
</html>
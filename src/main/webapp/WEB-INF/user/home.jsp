<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ecommerce.dao.ProductDAO" %>
<%@ page import="com.ecommerce.model.Category" %>
<%@ page import="com.ecommerce.dao.CategoryDAO" %>
<%@ page import="com.ecommerce.model.Product" %>
<%@ page import="java.util.List" %>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/user/login");
        return;
    }

    List<Product> products =
        (List<Product>) request.getAttribute("products");

    CategoryDAO categoryDAO = new CategoryDAO();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Home - E-Commerce</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <jsp:include page="../shared/navbar.jsp" />
    
    <div class="container">
        <div class="sidebar">
            <h3>Categories</h3>
            <ul class="category-list">
                <li><a href="${pageContext.request.contextPath}/user/home">All Products</a></li>
                <% for (Category cat : categoryDAO.getAllCategories()) { %>
                    <li>
                        <a href="${pageContext.request.contextPath}/user/home?categoryId=<%= cat.getCategoryId() %>">
                            <%= cat.getCategoryName() %>
                        </a>
                    </li>
                <% } %>
            </ul>
        </div>
        
        <div class="main-content">
            <div class="search-bar">
               <form action="${pageContext.request.contextPath}/user/search" method="get">
    <input type="text" name="query" placeholder="Search products">
    <button type="submit">Search</button>
</form>

            </div>
            
            <div class="product-grid">
                <% for (Product product : products) { %>
                    <div class="product-card">
                        <div class="product-image">
                            <img src="<%= request.getContextPath() + "/" + product.getImagePath() %>" 
                                 alt="<%= product.getProductName() %>">
                        </div>
                        <div class="product-info">
                            <h3><%= product.getProductName() %></h3>
                            <p class="category"><%= product.getCategoryName() %></p>
                            <p class="description"><%= product.getDescription() %></p>
                            <div class="product-footer">
                                <span class="price">$<%= String.format("%.2f", product.getPrice()) %></span>
                                <form action="${pageContext.request.contextPath}/cart/add" method="post">
    <input type="hidden" name="productId" value="<%= product.getProductId() %>">
    <input type="number" name="quantity" value="1" min="1">
    <button type="submit" class="btn small">Add to Cart</button>
</form>

                            </div>
                        </div>
                    </div>
                <% } %>
                
                <% if (products.isEmpty()) { %>
                    <div class="no-products">
                        <p>No products found in this category.</p>
                    </div>
                <% } %>
            </div>
        </div>
    </div>
</body>
</html>
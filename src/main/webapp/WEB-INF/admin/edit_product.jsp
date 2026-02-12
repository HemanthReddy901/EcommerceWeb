<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ecommerce.dao.ProductDAO" %>
<%@ page import="com.ecommerce.model.Product" %>
<%@ page import="java.util.List" %>
<%
    if (session.getAttribute("admin") == null) {
        response.sendRedirect(request.getContextPath() + "/admin-login.jsp");
        return;
    }

    String productIdParam = request.getParameter("id");
    if (productIdParam == null) {
        response.sendRedirect(request.getContextPath() + "/admin/manage-products.jsp");
        return;
    }

    int productId = Integer.parseInt(productIdParam);
    ProductDAO productDAO = new ProductDAO();
    Product product = productDAO.getProductById(productId);

    if (product == null) {
        response.sendRedirect(request.getContextPath() + "/admin/manage-products.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Product</title>
    <style>
        
        .container {
            width: 600px;
            margin: 80px auto 50px; /* top margin to avoid navbar */
            background: #fff;
            padding: 30px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            border-radius: 8px;
        }
        h2 {
            text-align: center;
            margin-bottom: 20px;
        }
        form label {
            display: block;
            margin: 10px 0 5px;
            font-weight: bold;
        }
        form input[type="text"],
        form input[type="number"],
        form input[type="file"],
        form textarea,
        form select {
            width: 100%;
            padding: 8px 10px;
            margin-bottom: 15px;
            border-radius: 5px;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }
        form textarea {
            resize: vertical;
        }
        .current-image {
            margin-bottom: 15px;
        }
        .current-image img {
            width: 150px;
            height: auto;
            border-radius: 5px;
            border: 1px solid #ccc;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            background: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            border: none;
            cursor: pointer;
            font-size: 14px;
        }
        .btn:hover {
            background: #0056b3;
        }
        .back-btn {
            display: block;
            margin-top: 20px;
            text-align: center;
            color: #555;
            text-decoration: none;
        }
    </style>
</head>
<body>



    <div class="container">
        <h2>Edit Product</h2>
        <form action="<%= request.getContextPath() %>/product/update" method="post" enctype="multipart/form-data">
            <input type="hidden" name="productId" value="<%= product.getProductId() %>">

            <label for="productName">Product Name:</label>
            <input type="text" id="productName" name="productName" value="<%= product.getProductName() %>" required>

            <label for="categoryId">Category:</label>
<select id="categoryId" name="categoryId" required>
    <%
        com.ecommerce.dao.CategoryDAO categoryDAO = new com.ecommerce.dao.CategoryDAO();
        List<com.ecommerce.model.Category> categories = categoryDAO.getAllCategories();
        for (com.ecommerce.model.Category cat : categories) {
            String selected = (cat.getCategoryId() == product.getCategoryId()) ? "selected" : "";
    %>
        <option value="<%= cat.getCategoryId() %>" <%= selected %>><%= cat.getCategoryName() %></option>
    <%
        }
    %>
</select>
            <label for="description">Description:</label>
            <textarea id="description" name="description" rows="4" required><%= product.getDescription() %></textarea>

            <label for="price">Price:</label>
            <input type="number" id="price" name="price" value="<%= product.getPrice() %>" step="0.01" required>

            <label for="quantity">Quantity:</label>
            <input type="number" id="quantity" name="quantity" value="<%= product.getQuantity() %>" required>

            <label>Current Image:</label>
            <div class="current-image">
                <img src="<%= request.getContextPath() + "/" + product.getImagePath() %>" alt="<%= product.getProductName() %>">
            </div>

            <label for="image">Upload New Image (optional):</label>
            <input type="file" id="image" name="image">

            <button type="submit" class="btn">Update Product</button>
        </form>

        <a href="<%= request.getContextPath() %>/admin/manage-products.jsp" class="back-btn">Back to Manage Products</a>
    </div>
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="com.ecommerce.dao.ProductDAO" %>
<%@ page import="com.ecommerce.model.Product" %>
<%@ page import="java.util.List" %>
<%
 if(session.getAttribute("admin") == null)
 {
	 response.sendRedirect(request.getContextPath() + "/admin-login.jsp");
	 return;
 }

ProductDAO productDAO = new ProductDAO();
List<Product> products = productDAO.getAllProducts();
%>
<!DOCTYPE html>
<html>
<head>
<title>Manage Products</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
     <jsp:include page="../shared/navbar.jsp"/>
     
     <div class="container">
       <h2>Manage Products</h2>
       
       <div class="admin-actions">
         <a href="${pageContext.request.contextPath}/admin/add-product" class="btn">Add New Product</a>
       </div>
       
       <% if (products.isEmpty()) { %>
       <div class="no-products">
         <p>No Products Found.</p>
       </div>
       <% } else { %>
       <div class="products-table">
         <table>
           <thead>
              <tr> 
                 <th>ID</th>
                 <th>Image</th>
                 <th>Product Name</th>
                 <th>Category</th>
                 <th>Price</th>
                 <th>Quantity</th>
                 <th>Action</th>
              </tr>
           </thead>
           <tbody>
             <% for (Product product: products) { %>
             <tr>
               <td><%= product.getProductId() %></td>
               <td><img src="<%= request.getContextPath() +"/" + product.getImagePath() %>" alt="<%= product.getProductName() %>" width="100"></td>
               <td><%= product.getProductName() %></td>
               <td><%= product.getCategoryName() %></td>
               <td><%= String.format("%.2f", product.getPrice()) %></td>
               <td><%= product.getQuantity() %></td>
               <td>
                  <div class="action-buttons">
                    <a href="${pageContext.request.contextPath}/admin/edit-product?id=<%= product.getProductId() %>" class="btn small">Edit</a>
                    <form action="${pageContext.request.contextPath}/product/delete" method="post" style="display:inline;">
                      <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                      <button type="submit" class="btn small danger" onClick="return confirm('Are You Sure?')">Delete</button>
                    </form>
                  </div>
               </td>
             </tr>
             <% } %>
           </tbody>
         </table>
       </div>
       <% } %>
       <div class="back-button">
         <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn-secondary">Back To Dashboard</a>
       </div>
     </div>
</body>
</html>
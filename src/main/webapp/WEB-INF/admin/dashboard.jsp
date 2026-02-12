<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ecommerce.dao.AdminDAO" %>
<%
    if (session.getAttribute("admin") == null) {
        response.sendRedirect(request.getContextPath() + "/admin-login.jsp");
        return;
    }
    
    AdminDAO adminDAO = new AdminDAO();
    int totalOrders = adminDAO.getTotalOrders();
    int deliveredOrders = adminDAO.getDeliveredOrders();
    double totalRevenue = adminDAO.getTotalRevenue();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <jsp:include page="../shared/navbar.jsp" />
    
    <div class="container">
        <h2>Admin Dashboard</h2>
        
        <div class="dashboard-cards">
            <div class="card">
                <h3>Total Orders</h3>
                <p><%= totalOrders %></p>
                <i class="fas fa-shopping-cart"></i>
            </div>
            <div class="card">
                <h3>Delivered Orders</h3>
                <p><%= deliveredOrders %></p>
                <i class="fas fa-check-circle"></i>
            </div>
            <div class="card">
                <h3>Total Revenue</h3>
                <p>$<%= String.format("%.2f", totalRevenue) %></p>
                <i class="fas fa-dollar-sign"></i>
            </div>
        </div>
        
         <div class="admin-actions">
        <a href="${pageContext.request.contextPath}/admin/add-category" class="btn">Add Category</a>


        <a href="${pageContext.request.contextPath}/admin/add-product" class="btn">
            Add Product
        </a>

        <a href="${pageContext.request.contextPath}/admin/view-orders" class="btn">
            View Orders
        </a>

        <a href="${pageContext.request.contextPath}/admin/manage-products" class="btn">
            Manage Products
        </a>
    </div>
    </div>
</body>
</html>
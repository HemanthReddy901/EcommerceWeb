<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String userType = null;
    String username = null;
    
    if (session.getAttribute("user") != null) {
        userType = "user";
        username = ((com.ecommerce.model.User) session.getAttribute("user")).getFullName();
    } else if (session.getAttribute("admin") != null) {
        userType = "admin";
        username = (String) session.getAttribute("admin");
    }
%>
<nav class="navbar">
    <div class="navbar-container">
        <a href="${pageContext.request.contextPath}/" class="navbar-brand">
            <i class="fas fa-store"></i> E-Shop
        </a>
        
        <ul class="navbar-menu">
            <% if (userType == null) { %>
                <li><a href="${pageContext.request.contextPath}/user/login">Login</a></li>
                <li><a href="${pageContext.request.contextPath}/user/register">Register</a></li>
                <li><a href="${pageContext.request.contextPath}/admin-login.jsp">Admin Login</a></li>
            <% } else if (userType.equals("user")) { %>
                <li><a href="${pageContext.request.contextPath}/user/home">Home</a></li>
              <li><a href="${pageContext.request.contextPath}/user/cart">Cart</a></li>
<li><a href="${pageContext.request.contextPath}/user/orders">My Orders</a></li>


                <li><a href="${pageContext.request.contextPath}/user/profile">Profile</a></li>
                <li>
                    <form action="${pageContext.request.contextPath}/auth/logout" method="post" style="display:inline;">
                        <button type="submit" class="btn-link">Logout</button>
                    </form>
                </li>
            <% } else if (userType.equals("admin")) { %>
                <li><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/add-category">Add Category</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/add-product">Add Product</a></li>
                <li> <a href="${pageContext.request.contextPath}/admin/view-orders">
            View Orders
        </a></li>
                <li><a href="${pageContext.request.contextPath}/admin/manage-products">Manage Products</a></li>
                <li>
                    <form action="${pageContext.request.contextPath}/auth/logout" method="post" style="display:inline;">
                        <button type="submit" class="btn-link">Logout</button>
                    </form>
                </li>
            <% } %>
        </ul>
        
        <% if (username != null) { %>
            <div class="navbar-user">
                <span class="welcome-text">Welcome, <%= username %>!</span>
            </div>
        <% } %>
    </div>
</nav>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ecommerce.dao.OrderDAO" %>
<%@ page import="com.ecommerce.model.Order" %>
<%@ page import="java.util.List" %>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/user/login");
        return;
    }

    List<Order> orders = (List<Order>) request.getAttribute("orders");
%>
<!DOCTYPE html>
<html>
<head>
    <title>My Orders</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="../shared/navbar.jsp" />
    
    <div class="container">
        <h2>My Orders</h2>
        
        <% if (request.getParameter("success") != null) { %>
            <div class="alert success"><%= request.getParameter("success") %></div>
        <% } %>
        
        <% if (orders.isEmpty()) { %>
            <div class="no-orders">
                <i class="fas fa-box-open fa-3x"></i>
                <h3>No orders yet</h3>
                <p>Start shopping to see your orders here!</p>
                <a href="home.jsp" class="btn">Start Shopping</a>
            </div>
        <% } else { %>
            <div class="orders-list">
                <% for (Order order : orders) { %>
                    <div class="order-card">
                        <div class="order-header">
                            <div>
                                <h3>Order #<%= order.getOrderId() %></h3>
                                <p class="order-date">Placed on: <%= order.getOrderDate() %></p>
                            </div>
                            <div>
                                <span class="status <%= order.getStatus() %>">
                                    <%= order.getStatus() %>
                                </span>
                            </div>
                        </div>
                        
                        <div class="order-details">
                            <p><strong>Total Amount:</strong> $<%= String.format("%.2f", order.getTotalAmount()) %></p>
                        </div>
                        
                        <div class="order-actions">
                            <a href="<%= request.getContextPath() %>/order/view?id=<%= order.getOrderId() %>"
   class="btn small">
    <i class="fas fa-eye"></i> View Details
</a>



                        </div>
                    </div>
                <% } %>
            </div>
        <% } %>
    </div>
</body>
</html>
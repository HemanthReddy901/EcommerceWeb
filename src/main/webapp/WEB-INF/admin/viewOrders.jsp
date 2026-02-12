<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ecommerce.dao.OrderDAO" %>
<%@ page import="com.ecommerce.model.Order" %>
<%@ page import="java.util.List" %>
<%
    if (session.getAttribute("admin") == null) {
        response.sendRedirect(request.getContextPath() + "/admin-login.jsp");
        return;
    }
    
    OrderDAO orderDAO = new OrderDAO();
    List<Order> orders = orderDAO.getAllOrders();
%>
<!DOCTYPE html>
<html>
<head>
    <title>View Orders</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <jsp:include page="../shared/navbar.jsp" />
    
    <div class="container">
        <h2>All Orders</h2>
        
        <% if (request.getParameter("success") != null) { %>
            <div class="alert success"><%= request.getParameter("success") %></div>
        <% } %>
        
        <% if (request.getParameter("error") != null) { %>
            <div class="alert error"><%= request.getParameter("error") %></div>
        <% } %>
        
        <% if (orders.isEmpty()) { %>
            <div class="no-orders">
                <p>No orders found.</p>
            </div>
        <% } else { %>
            <div class="orders-table">
                <table>
                    <thead>
                        <tr>
                            <th>Order ID</th>
                            <th>Customer</th>
                            <th>Date</th>
                            <th>Total</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Order order : orders) { %>
                            <tr>
                                <td>#<%= order.getOrderId() %></td>
                                <td><%= order.getUserName() %></td>
                                <td><%= order.getOrderDate() %></td>
                                <td>$<%= String.format("%.2f", order.getTotalAmount()) %></td>
                                <td>
                                    <span class="status <%= order.getStatus() %>">
                                        <%= order.getStatus() %>
                                    </span>
                                </td>
                                <td>
                                    <div class="action-buttons">
                                        <a href="<%= request.getContextPath() %>/order/admin-view?id=<%= order.getOrderId() %>" class="btn small">
    <i class="fas fa-eye"></i> View
</a>
                                        <form action="${pageContext.request.contextPath}/order/update" method="post" style="display:inline;">
                                            <input type="hidden" name="orderId" value="<%= order.getOrderId() %>">
                                            <select name="status" onchange="this.form.submit()" class="status-select">
                                                <option value="pending" <%= order.getStatus().equals("pending") ? "selected" : "" %>>Pending</option>
                                                <option value="processing" <%= order.getStatus().equals("processing") ? "selected" : "" %>>Processing</option>
                                                <option value="shipped" <%= order.getStatus().equals("shipped") ? "selected" : "" %>>Shipped</option>
                                                <option value="delivered" <%= order.getStatus().equals("delivered") ? "selected" : "" %>>Delivered</option>
                                                <option value="cancelled" <%= order.getStatus().equals("cancelled") ? "selected" : "" %>>Cancelled</option>
                                            </select>
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
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn secondary">Back to Dashboard</a>
        </div>
    </div>
</body>
</html>
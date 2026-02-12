<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ecommerce.model.User" %>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/user/login.jsp");
        return;
    }
    
    User user = (User) session.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
    <title>My Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="../shared/navbar.jsp" />
    
    <div class="container">
        <h2>My Profile</h2>
        
        <div class="profile-card">
            <div class="profile-header">
                <div class="profile-avatar">
                    <i class="fas fa-user-circle fa-4x"></i>
                </div>
                <div class="profile-info">
                    <h3><%= user.getFullName() %></h3>
                    <p class="profile-email"><%= user.getEmail() %></p>
                </div>
            </div>
            
            <div class="profile-details">
                <div class="detail-item">
                    <span class="detail-label">Full Name:</span>
                    <span class="detail-value"><%= user.getFullName() %></span>
                </div>
                <div class="detail-item">
                    <span class="detail-label">Email:</span>
                    <span class="detail-value"><%= user.getEmail() %></span>
                </div>
                <div class="detail-item">
                    <span class="detail-label">Phone:</span>
                    <span class="detail-value"><%= user.getPhone() %></span>
                </div>
                <div class="detail-item">
                    <span class="detail-label">Address:</span>
                    <span class="detail-value"><%= user.getAddress() %></span>
                </div>
                <div class="detail-item">
                    <span class="detail-label">Member Since:</span>
                    <span class="detail-value">Registered User</span>
                </div>
            </div>
            
            <div class="profile-actions">
                 <a href="${pageContext.request.contextPath}/user/edit-profile" class="btn">Edit Profile</a>
                <a href="${pageContext.request.contextPath}/order/user" class="btn secondary">View Orders</a>
            </div>
        </div>
    </div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.ecommerce.model.User" %>
<% 
  if(session.getAttribute("user") == null) 
  {
	  response.sendRedirect(request.getContextPath()+"/user/login");
	  return;
  }

User user=(User) request.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
<title>Edit Profile</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
  <jsp:include page="../shared/navbar.jsp"/>
  
  <div class="container">
    <h2>Edit Profile</h2>
    
    <% if(request.getAttribute("error") != null) { %>
    <div class="alert error"><%= request.getAttribute("error") %></div>
    <% } %>
    
    <form action="${pageContext.request.contextPath}/user/update-profile" method="post" class="profile-form">
      <div class="form-group">
        <label>Full Name</label>
        <input type="text" name="fullName" value="<%= user.getFullName() %>" required>
        
      </div>
      <div class="form-group">
        <label>Email</label>
        <input  type="email" name="email" value="<%= user.getEmail() %>" required>
      </div>
      <div class="form-group">
        <label>Phone</label>
        <input type="text" name="phone" value="<%= user.getPhone() %>" required>
      </div>
      <div class="form-group">
        <label>Address</label>
        <textarea name="address"><%= user.getAddress() %></textarea> 
      </div>
      <div class="form-actions">
        <button type="submit" class="btn">Update Profile</button>
        <a href="${pageContext.request.contextPath}/user/profile" class="btn secondary">Cancel</a>
      </div>
    </form>
  </div>
</body>
</html>
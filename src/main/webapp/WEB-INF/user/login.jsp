<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>User Login</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
       <div class="auth-form">
         <h2>User Login</h2>
         <% if (request.getParameter("success") != null) { %>
         <div class="alert success"><%= request.getParameter("success") %></div>
         <% } %>
         
         <% if (request.getAttribute("error") != null){  %>
         <div class="alert-success"><%= request.getAttribute("error") %></div>
         <% } %>
         
         <form action="${pageContext.request.contextPath}/auth/login" method="post" class="form">
           <div class="form-group">
             <label>Email:</label>
             <input type="email" name="email" id="email" required/>
           </div>
           <div class="form-group">
             <label>Password:</label>
             <input type="password" id="password" name="password" required/>
           </div>
           <div class="form-group">
             <button type="submit" class="btn">Login</button>
             <a href="${pageContext.request.contextPath}/user/register" class="btn secondary">
              Register Instead
             </a>
           </div>
         </form>
         
         <div class="admin-login-link">
           <p>Are You A ShopOwner?<a href="${pageContext.request.contextPath}/admin-login.jsp">Login As Admin</a></p>
         </div>
       </div>
    </div>
</body>
</html>
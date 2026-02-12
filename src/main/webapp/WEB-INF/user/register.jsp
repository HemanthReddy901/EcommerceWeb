<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Registration</title>
 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
  <div class="container">
     <div class="auth-form">
        <h2>User Registration</h2>
        <% if(request.getParameter("error")!=null){ %>
    	  <div class="alert error"><%= request.getParameter("error") %></div>
      <% } %>
       <form action="${pageContext.request.contextPath}/auth/register" method="post" class="form">
      <div class="form-group">
         <label for="fullName">Full Name:</label>
         <input type="text" id="fullName" name="fullName" required />
      </div>
       <div class="form-group">
         <label for="email">Email:</label>
         <input type="email" id="email" name="email" required />
      </div>
      <div class="form-group">
         <label for="password">Password:</label>
         <input type="password" id="password" name="password" required />
      </div>
      <div class="form-group">
         <label for="address">Address:</label>
         <textarea rows="3" id="address" name="address" required ></textarea>
      </div>
      <div class="form-group">
         <label for="phone">Phone:</label>
         <input type="tel" id="phone" name="phone" required />
      </div>
       <div class="form-group">
         <button type="submit" class="btn">Register</button>
         <a href="${pageContext.request.contextPath}/user/login" class="btn secondary">
    Login Instead
</a>

      </div>
   </form>
     </div>
  </div>
</body>
</html>
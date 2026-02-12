<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Login</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
<div class="container">
  <div class="auth-form">
    <h2><i class="fas fa-user-shield"></i>Admin Login</h2>
  
    <% if(request.getAttribute("error") != null) { %>
     <div class="alert-error">
        <i class="fas fa-exclamation-circle"></i> <%= request.getAttribute("error") %>
     </div>
     <% } %>
     
     <form action="<%=request.getContextPath()%>/auth/admin-login" method="post" class="form">
       <div class="form-group">
         <label for="username">
         <i class="fas fa-user"></i> Username
         </label>
         <input type="text" id="username" name="username" placeholder="Enter username" required >
       </div>
       <div class="form-group">
         <label for="password">
           <i class="fas fa-lock"></i> Password
         </label>
         <input type="password" id="password" name="password" placeholder="Enter Password" required>
       </div>
       
       <div class="form-group">
          <button type="submit" class="btn">
          <i class="fas fa-sign-in-alt"></i> Login
          </button>
       
       </div>
     </form>
     <div class="admin-login-link">
      <p>Not an admin? <a href="<%=request.getContextPath() %>/user/login">User Login</a></p>
     </div>
  </div>
</div>
</body>
</html>
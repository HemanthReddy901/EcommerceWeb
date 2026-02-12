<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
    if(session.getAttribute("admin") == null)
    {
    	response.sendRedirect(request.getContextPath()+"/admin-login.jsp");
    	return;
    }
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Category</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
 <jsp:include page="../shared/navbar.jsp" />
 
   <div class="container">
     <h2>Add New Category</h2>
     <% if(request.getParameter("succuss") != null) { %>
     <div class="alert success"><%= request.getParameter("succuss") %></div>
     <% } %>
      <% if(request.getParameter("error") != null) { %>
     <div class="alert error"><%= request.getParameter("error") %></div>
     <% } %>
     <form action="${pageContext.request.contextPath}/category/add" method="post" class="form">
       <div class="form-group">
         <label for="categoryName">Category Name</label>
         <input type="text" id="categoryName" name="categoryName" required/>
       </div>
       <div class="form-group">
        <button type="submit" class="btn">Add Category</button>
        <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn secondary">
        Cancel
        </a>
       </div>
     </form>
   </div>
</body>
</html>
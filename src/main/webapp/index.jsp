<!DOCTYPE html>
<html>
<head>
<title>Welcome to E-Shop</title>
 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
  <jsp:include page="/WEB-INF/shared/navbar.jsp" />
  
  
  <div class="hero-section">
     <div class="hero-content">
       <h1>Welcome to E-Shop</h1>
       <p>Your one-stop destination for all shopping needs</p>
       <div class="hero-button">
         <a href="${pageContext.request.contextPath}/user/register" class="btn">Get Started</a>
         <a href="${pageContext.request.contextPath}/user/login" class="btn secondary">Login</a>
       </div>
     
     </div>
  </div>
  
  <div class="container">
    <div class="features">
      <div class="feature-card">
        <i class="fas fa-shopping-cart fa-3x"></i>
        <h3>Easy Shopping</h3>
        <p>Browse and shop from our wide range of products</p>
      </div>
      <div class="feature-card">
        <i class="fas fa-shipping-fast fa-3x"></i>
        <h3>Fast Delivery</h3>
        <p>Quick and reliable delivery to your doorstep</p>
      </div>
      <div class="feature-card">
        <i class="fas fa-shield-alt fa-3x"></i>
        <h3>Secure Payment</h3>
        <p>Safe and Secure Payment options</p>
      </div>
    </div>
  </div>
</body>
</html>
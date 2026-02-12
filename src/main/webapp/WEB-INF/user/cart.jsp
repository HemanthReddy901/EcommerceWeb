<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ecommerce.dao.CartDAO" %>
<%@ page import="com.ecommerce.model.CartItem" %>
<%@ page import="java.util.List" %>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/user/login.jsp");
        return;
    }
    

List<CartItem> cartItems =
    (List<CartItem>) request.getAttribute("cartItems");
double cartTotal =
    (Double) request.getAttribute("cartTotal");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Shopping Cart</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <jsp:include page="../shared/navbar.jsp" />
    
    <div class="container">
        <h2>Shopping Cart</h2>
        
        
        
        <% if (request.getParameter("error") != null) { %>
            <div class="alert error"><%= request.getParameter("error") %></div>
        <% } %>
        
        <% if (cartItems.isEmpty()) { %>
            <div class="empty-cart">
                <i class="fas fa-shopping-cart fa-3x"></i>
                <h3>Your cart is empty</h3>
                <p>Add some products to your cart!</p>
                <a href="${pageContext.request.contextPath}/user/home" class="btn">Continue Shopping</a>
            </div>
        <% } else { %>
            <div class="cart-items">
                <table>
                    <thead>
                        <tr>
                            <th>Product</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Total</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (CartItem item : cartItems) { %>
                            <tr class="cart-item">
                                <td>
                                    <div class="cart-item-details">
                                        <img src="<%= request.getContextPath() + "/" + item.getImagePath() %>" 
                                             alt="<%= item.getProductName() %>" class="cart-item-image">
                                        <div>
                                            <h4><%= item.getProductName() %></h4>
                                        </div>
                                    </div>
                                </td>
                                <td>$<%= String.format("%.2f", item.getPrice()) %></td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/cart/update" method="post" class="quantity-form">
                                        <input type="hidden" name="cartId" value="<%= item.getCartId() %>">
                                        <input type="number" name="quantity" value="<%= item.getQuantity() %>" min="1" class="quantity-input">
                                        <button type="submit" class="btn small">Update</button>
                                    </form>
                                </td>
                                <td>$<%= String.format("%.2f", item.getPrice() * item.getQuantity()) %></td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/cart/remove" method="post" style="display:inline;">
                                        <input type="hidden" name="cartId" value="<%= item.getCartId() %>">
                                        <button type="submit" class="btn small danger">
                                            <i class="fas fa-trash"></i> Remove
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
                
                <div class="cart-summary">
                    <div class="summary-details">
                        <h3>Order Summary</h3>
                        <div class="summary-row">
                            <span>Subtotal:</span>
                            <span>$<%= String.format("%.2f", cartTotal) %></span>
                        </div>
                        <div class="summary-row">
                            <span>Shipping:</span>
                            <span>Free</span>
                        </div>
                        <div class="summary-row total">
                            <span>Total:</span>
                            <span>$<%= String.format("%.2f", cartTotal) %></span>
                        </div>
                    </div>
                    
                    <div class="cart-actions">
                        <a href="${pageContext.request.contextPath}/user/home" class="btn secondary">Continue Shopping</a>
                        <form action="${pageContext.request.contextPath}/cart/checkout" method="post">
                            <button type="submit" class="btn">Proceed to Checkout</button>
                        </form>
                    </div>
                </div>
            </div>
        <% } %>
    </div>
</body>
</html>
package com.ecommerce.controller;

import com.ecommerce.dao.CartDAO;
import com.ecommerce.dao.OrderDAO;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Order;
import com.ecommerce.model.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/cart/*")
public class CartServlet extends HttpServlet {
    private CartDAO cartDAO;
    private OrderDAO orderDAO;
    
    @Override
    public void init() {
        cartDAO = new CartDAO();
        orderDAO = new OrderDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        String action = request.getPathInfo();
        try {
        if (action == null || action.equals("/view")) {
            
            List<CartItem> cartItems = cartDAO.getCartItems(user.getUserId());
            double cartTotal = cartDAO.getCartTotal(user.getUserId());
            
            request.setAttribute("cartItems", cartItems);
            request.setAttribute("cartTotal", cartTotal);
            
            request.getRequestDispatcher("/WEB-INF/user/cart.jsp")
            .forward(request, response);


        }
        }catch(Exception e)
        {
        	e.printStackTrace();
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/user/login.jsp");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        String action = request.getPathInfo();
        
        if (action.equals("/add")) {
            addToCart(request, response, user);
        } else if (action.equals("/update")) {
            updateCart(request, response, user);
        } else if (action.equals("/remove")) {
            removeFromCart(request, response, user);
        } else if (action.equals("/checkout")) {
            checkout(request, response, user);
        }
    }
    
    private void addToCart(HttpServletRequest request, HttpServletResponse response, User user) 
            throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
         try {
        if (cartDAO.addToCart(user.getUserId(), productId, quantity)) {
            response.sendRedirect(request.getContextPath() + "/cart/view?success=Product added to cart!");
        } else {
            response.sendRedirect(request.getContextPath() + "/cart/view?error=Failed to add product to cart!");
        }
         }
         catch(Exception e)
         {
        	 e.printStackTrace();
         }
    }
    
    private void updateCart(HttpServletRequest request, HttpServletResponse response, User user) 
            throws ServletException, IOException {
        int cartId = Integer.parseInt(request.getParameter("cartId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        try {
        if (cartDAO.updateCartQuantity(cartId, user.getUserId(), quantity)) {
            response.sendRedirect(request.getContextPath() + "/cart/view?success=Cart updated!");
        } else {
            response.sendRedirect(request.getContextPath() + "/cart/view?error=Failed to update cart!");
        }
        }
        catch(Exception e)
        {
       	 e.printStackTrace();
        }
    }
    
    private void removeFromCart(HttpServletRequest request, HttpServletResponse response, User user) 
            throws IOException {
        int cartId = Integer.parseInt(request.getParameter("cartId"));
        try {
        if (cartDAO.removeFromCart(cartId, user.getUserId())) {
            response.sendRedirect(request.getContextPath() + "/cart/view?success=Item removed from cart!");
        } else {
            response.sendRedirect(request.getContextPath() + "/cart/view?error=Failed to remove item!");
        }
        }
        catch(Exception e)
        {
       	 e.printStackTrace();
        }
    }
    
    private void checkout(HttpServletRequest request, HttpServletResponse response, User user) 
            throws ServletException, IOException {
       try {
        List<CartItem> cartItems = cartDAO.getCartItems(user.getUserId());
        double totalAmount = cartDAO.getCartTotal(user.getUserId());
        
        if (cartItems.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart/view?error=Cart is empty!");
            return;
        }
        
        
        Order order = new Order();
        order.setUserId(user.getUserId());
        order.setTotalAmount(totalAmount);
        
        int orderId = orderDAO.createOrder(order, cartItems);
        
        if (orderId > 0) {
            
            cartDAO.clearCart(user.getUserId());
            response.sendRedirect(request.getContextPath() + "/order/view?id=" + orderId + "&success=Order placed successfully!");
        } else {
            response.sendRedirect(request.getContextPath() + "/cart/view?error=Failed to place order!");
        }
       }
       catch(Exception e)
       {
      	 e.printStackTrace();
       }
    }
}
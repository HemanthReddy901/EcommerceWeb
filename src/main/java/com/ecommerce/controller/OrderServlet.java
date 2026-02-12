		package com.ecommerce.controller;

import com.ecommerce.dao.OrderDAO;
import com.ecommerce.model.Order;
import com.ecommerce.model.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/order/*")
public class OrderServlet extends HttpServlet {
    private OrderDAO orderDAO;
    
    @Override
    public void init() {
        orderDAO = new OrderDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getPathInfo();
        System.out.println(action);
        if (action == null || action.equals("/view")) {
            viewOrder(request, response);          // USER
        } else if (action.equals("/admin-view")) {
            viewOrderAsAdmin(request, response);  // ADMIN
        } else if (action.equals("/all")) {
            viewAllOrders(request, response);
        } else if (action.equals("/user")) {
            viewUserOrders(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getPathInfo();
        
        if (action.equals("/update")) {
            updateOrderStatus(request, response);
        }
    }
    
    private void viewOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }

        String orderIdParam = request.getParameter("id");
        if (orderIdParam == null) {
            response.sendRedirect(request.getContextPath() + "/order/user");
            return;
        }
        
        int orderId = Integer.parseInt(orderIdParam);
        Order order = orderDAO.getOrderById(orderId);

        if (order == null) {
            response.sendRedirect(request.getContextPath() + "/order/user?error=Order not found");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (order.getUserId() != user.getUserId()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        request.setAttribute("order", order);
        request.setAttribute("success", request.getParameter("success"));
           
        request.getRequestDispatcher("/WEB-INF/user/order_details.jsp")
               .forward(request, response);
    }


    
    private void viewAllOrders(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/admin-login.jsp");
            return;
        }
        
        List<Order> orders = orderDAO.getAllOrders();
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/WEB-INF/admin/viewOrders.jsp")
        .forward(request, response);

    }
    
    private void viewUserOrders(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
       
            response.sendRedirect(request.getContextPath() + "/user/login.jsp");
            return;
        }
        
        User user = (User) session.getAttribute("user");
     	System.out.println(user);
        List<Order> orders = orderDAO.getOrdersByUser(user.getUserId());
        request.setAttribute("orders", orders);
        
        request.getRequestDispatcher("/WEB-INF/user/orders.jsp")
        .forward(request, response);

    }
    private void viewOrderAsAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        

        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/admin-login.jsp");
            return;
        }

        String orderIdParam = request.getParameter("id");
        if (orderIdParam == null) {
            response.sendRedirect(request.getContextPath() + "/order/all");
            return;
        }

        int orderId = Integer.parseInt(orderIdParam);
        Order order = orderDAO.getOrderById(orderId);

        if (order == null) {
            response.sendRedirect(request.getContextPath() + "/order/all?error=Order not found");
            return;
        }

        request.setAttribute("order", order);

        request.getRequestDispatcher("/WEB-INF/admin/order_details.jsp").forward(request, response);
    }
    
    private void updateOrderStatus(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/admin-login.jsp");
            return;
        }
        
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String status = request.getParameter("status");
        
        if (orderDAO.updateOrderStatus(orderId, status)) {
            response.sendRedirect(request.getContextPath() + "/order/all?success=Order status updated!");
        } else {
            response.sendRedirect(request.getContextPath() + "/order/all?error=Failed to update order status!");
        }
    }
}
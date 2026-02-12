package com.ecommerce.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import com.ecommerce.dao.CartDAO;
import com.ecommerce.dao.OrderDAO;
import com.ecommerce.dao.ProductDAO;
import com.ecommerce.dao.UserDAO;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Order;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;


@WebServlet("/user/*")
public class UserServlet extends HttpServlet {
	private UserDAO userDAO;
	private CartDAO cartDAO;
	private OrderDAO orderDAO;
	private ProductDAO productDAO;
	
	@Override
	public void init()
	{
	  userDAO=new UserDAO();
	  cartDAO=new CartDAO();
	  orderDAO=new OrderDAO();
	  productDAO=new ProductDAO();
	  
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{
		String action = request.getPathInfo();
		
		if(action == null || "/home".equals(action) || "/".equals(action)) {
		    String categoryIdStr = request.getParameter("categoryId");
		    List<Product> products;

		    if(categoryIdStr != null && !categoryIdStr.isEmpty()) {
		        int categoryId = Integer.parseInt(categoryIdStr);
		        products = productDAO.getProductByCategory(categoryId);
		    } else {
		        products = productDAO.getAllProducts();
		    }

		    request.setAttribute("products", products);
		    request.getRequestDispatcher("/WEB-INF/user/home.jsp").forward(request, response);
		}

		else if("/login".equals(action))
		{
			request.getRequestDispatcher("/WEB-INF/user/login.jsp").forward(request, response);

		}
		else if("/register".equals(action))
		{
			request.getRequestDispatcher("/WEB-INF/user/register.jsp").forward(request, response);

		}
		else if("/profile".equals(action))
		{
           HttpSession session=request.getSession(false);
           if(session == null || session.getAttribute("user") == null)
           {
        	   response.sendRedirect(request.getContentType()+"/user/login");
            return;
           }
			request.getRequestDispatcher("/WEB-INF/user/profile.jsp").forward(request, response);

		}
		else if("/cart".equals(action))
		{
			 HttpSession session=request.getSession(false);
	         if(session == null || session.getAttribute("user") == null)
	         {
	        	 response.sendRedirect(request.getContentType()+"/user/login");
	         return;
	         }
	         int userId =((User) session.getAttribute("user")).getUserId();
	         List<CartItem> cartItems=cartDAO.getCartItems(userId);
	         double cartTotal=cartDAO.getCartTotal(userId);
	         request.setAttribute("cartItems", cartItems);
	         request.setAttribute("cartTotal", cartTotal);
	         request.getRequestDispatcher("/WEB-INF/user/cart.jsp").forward(request, response);
		}
		else if("/orders".equals(action))
		{
			HttpSession session=request.getSession(false);
	        if(session == null || session.getAttribute("user") == null)
	        {
	        	response.sendRedirect(request.getContentType()+"/user/login");
	        return;
	        }
	        int userId=((User) session.getAttribute("user")).getUserId();
	        List<Order> orders= orderDAO.getOrdersByUser(userId);
	        request.setAttribute("orders", orders);
	        request.getRequestDispatcher("/WEB-INF/user/orders.jsp").forward(request, response);
	        
		}
		else if("/search".equals(action))
		{
			String query = request.getParameter("query");
			
			List<Product> products;
			if(query != null && !query.trim().isEmpty())
			{
				products=productDAO.searchProducts(query);
			}
			else
			{
				products = productDAO.getAllProducts();
			}
			
			request.setAttribute("products", products);
			request.setAttribute("searchQuery", query);
			
			request.getRequestDispatcher("/WEB-INF/user/home.jsp").forward(request, response);
		}
		
		else if("/order-details".equals(action))
		{
			HttpSession session=request.getSession(false);
	        if(session == null || session.getAttribute("user") == null)
	        {
	        	response.sendRedirect(request.getContentType()+"/user/login");
	        return;
	        }
	        
	        int orderId = Integer.parseInt(request.getParameter("id"));
	        Order order = orderDAO.getOrderById(orderId);
	        request.setAttribute("order", order);
	        request.getRequestDispatcher("/WEB-INF/user/order_details.jsp").forward(request, response);
		}
		
		else if("edit-profile".equals(action))
		{
			HttpSession session=request.getSession(false);
	        if(session == null || session.getAttribute("user") == null)
	        {
	        	response.sendRedirect(request.getContentType()+"/user/login");
	        return;
	        }
	        User user=(User) session.getAttribute("user");
		    request.setAttribute("user", user);
		    request.getRequestDispatcher("/WEB-INF/user/edit_profile.jsp").forward(request, response);
		    
		}
		else if("/logout".equals(action))
		{
			HttpSession session=request.getSession(false);
			if(session == null)
			{
				session.invalidate();
			}
			response.sendRedirect(request.getContextPath()+"/user/login");
		}
		else
		{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found");
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action=request.getPathInfo();
		
		if("/login".equals(action))
		{
			String email=request.getParameter("email");
			String password=request.getParameter("password");
			User user=UserDAO.loginUser(email, password);
			if(user != null)
			{
				HttpSession session= request.getSession();
				session.setAttribute("user", user);
				response.sendRedirect(request.getContextPath()+"/user/home");
				
			}
			else
			{
				request.setAttribute("error", "Invalid email or password");
				request.getRequestDispatcher("/WEB-INF/user/login.jsp").forward(request, response);
			}
		}
		else if("/register".equals(action))
		{
			String email =request.getParameter("email");
			if(userDAO.checkEmailExists(email))
			{
				request.setAttribute("error", "Email Already Exists");
				request.getRequestDispatcher("/WEB-INF/user/register.jsp").forward(request, response);
				return;
			}
			User user = new User();
			user.setFullName(request.getParameter("fullName"));
			user.setEmail(email);
			user.setPassword(request.getParameter("password"));
			user.setAddress(request.getParameter("address"));
			user.setPhone(request.getParameter("phone"));
			
			if (userDAO.registerUser(user))
			{
				response.sendRedirect(request.getContextPath()+"/user/login?success=Registered Successfully");
				
			}
			else
			{
				request.setAttribute("error", "Registeration Failed");
				request.getRequestDispatcher("/WEB-INF/user/register.jsp").forward(request, response);
				
			}
		}
		else if("/update-profile".equals(action))
		{
			HttpSession session=request.getSession(false);
	        if(session == null || session.getAttribute("user") == null)
	        {
	        	response.sendRedirect(request.getContentType()+"/user/login");
	        return;
	        }
	        
	        User user= (User) session.getAttribute("user");
	        
	        String fullName = request.getParameter("fullName");
	        String email = request.getParameter("email");
	        String phone = request.getParameter("phone");
	        String address = request.getParameter("address");
	        
	        user.setFullName(fullName);
	        user.setEmail(email);
	        user.setPhone(phone);
	        user.setAddress(address);
	        
	        boolean updated = userDAO.updateUser(user);
	        
	        if(updated)
	        {
	        	session.setAttribute("user", user);
	        	response.sendRedirect(request.getContextPath()+"/user/profile?success=Profile Updates!");
	        	
	        }
	        else
	        {
	        	request.setAttribute("error", "Failed to update profile");
	        	request.getRequestDispatcher("/WEB-INF/user/edit_profile.jsp").forward(request, response);
	        }
		}
		
	}

}

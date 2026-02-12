package com.ecommerce.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.ecommerce.dao.AdminDAO;
import com.ecommerce.dao.UserDAO;
import com.ecommerce.model.User;
@WebServlet("/auth/*")
public class AuthServlet extends HttpServlet {
	private UserDAO userDAO;
	private AdminDAO adminDAO;
	@Override
	public void init() {
		userDAO=new UserDAO();
		adminDAO=new AdminDAO();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action=request.getPathInfo();
		switch(action) {
		case "/register": registerUser(request,response);break;
		case "/login":LoginUser(request,response);break;
		case "/admin-login":loginAdmin(request,response);break;
		case "/logout":logout(request,response);break;
		}
	}
	private void registerUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String email = request.getParameter("email").trim();
	    String password = request.getParameter("password").trim();
	    String fullName = request.getParameter("fullName").trim();
	    String address = request.getParameter("address").trim();
	    String phone = request.getParameter("phone").trim();

	    if(userDAO.checkEmailExists(email)) {
	        request.setAttribute("error","Email already Exists!");
	        request.getRequestDispatcher("/user/register.jsp").forward(request, response);
	        return;
	    }

	    User user = new User();
	    user.setEmail(email);
	    user.setPassword(password);
	    user.setFullName(fullName);
	    user.setAddress(address);
	    user.setPhone(phone);

	    boolean registered = userDAO.registerUser(user);
	    System.out.println("User registration result: " + registered);

	    if(registered) {  
	    	request.setAttribute("success", "Registration successful");
	    request.getRequestDispatcher("/WEB-INF/user/login.jsp").forward(request, response);
	    } else {
	        request.setAttribute("error","Registration Failed");
	        request.getRequestDispatcher("/user/register.jsp").forward(request, response);
	    }
	}

	private void LoginUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	    String email = request.getParameter("email").trim();
	    String password = request.getParameter("password").trim();

	    User user = UserDAO.loginUser(email, password);
	    System.out.println("Login attempt: " + email + "/" + password);
	    System.out.println("User returned: " + user);

	    if (user != null) {
	        HttpSession session = request.getSession();
	        session.setAttribute("user", user);
	        response.sendRedirect(request.getContextPath() + "/user/home");
	    } else {
	        request.setAttribute("error", "Invalid email or Password");
	        request.getRequestDispatcher("/user/login.jsp").forward(request, response);
	    }
	}

	private void loginAdmin(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		if(adminDAO.validateAdmin(username, password)) {
			HttpSession session=request.getSession();
			session.setAttribute("admin",username);
			response.sendRedirect(request.getContextPath()+"/admin/dashboard");
		}else {
			request.setAttribute("error","Invalid email or Password");
			request.getRequestDispatcher("/admin-login.jsp").forward(request, response);
		}
	}
	private void logout(HttpServletRequest request,HttpServletResponse response) throws IOException {
		HttpSession session=request.getSession(false);
		if(session!=null) {
			session.invalidate();
		}
		response.sendRedirect(request.getContextPath()+"/");
	}
}
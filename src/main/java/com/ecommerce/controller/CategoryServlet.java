package com.ecommerce.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.ecommerce.dao.CategoryDAO;
import com.ecommerce.model.Category;

@WebServlet("/category/*")
public class CategoryServlet extends HttpServlet 
{
	private CategoryDAO categoryDAO;
	@Override
	 public void init()
	 {
		 categoryDAO=new CategoryDAO();
	 }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action=request.getPathInfo();
		if("/all".equals(action))
		{
			request.setAttribute("categories", categoryDAO.getAllCategories());
			request.getRequestDispatcher("/admin/manageproducts.jsp").forward(request, response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(false);
		if(session==null || session.getAttribute("admin")==null)
		{
			response.sendRedirect(request.getContextPath()+"/admin-login.jsp");
			return;
		}
		String action=request.getPathInfo();
		if("/add".equals(action))
		{
			addCategory(request,response);
		}
	}
	private void addCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String categoryName=request.getParameter("categoryName");
		Category category=new Category();
		category.setCategoryName(categoryName);
		if(categoryDAO.addCategory(category)) {
			response.sendRedirect(request.getContextPath()+"/admin/add-category?success=Category Added Succussfully");
		}else 
		{
			response.sendRedirect(
	                request.getContextPath()+ "/admin/add-category?error=Failed to add Category");
		}
	}

}

package com.ecommerce.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.ecommerce.dao.AdminDAO;
import com.ecommerce.dao.CategoryDAO;


@WebServlet("/admin/*")
public class AdminServlet extends HttpServlet {
	 
	private AdminDAO adminDAO;
	
	@Override
    public void init()
    {
		adminDAO=new AdminDAO();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(false);
		if(session == null || session.getAttribute("admin")==null)
		{
			response.sendRedirect(request.getContextPath()+"/admin-login.jsp");
			return;
		}
		String action = request.getPathInfo();
		
		if(action == null || "/dashboard".equals(action))
		{
			request.setAttribute("totalOrders", adminDAO.getTotalOrders());
			request.setAttribute("deliveredOrders", adminDAO.getDeliveredOrders());
			request.setAttribute("totalRevenue",  adminDAO.getTotalRevenue());
			request.getRequestDispatcher("/WEB-INF/admin/dashboard.jsp").forward(request, response);
			return;
		}
		if("/add-category".equals(action))
		{
		    request.getRequestDispatcher("/WEB-INF/admin/addCategory.jsp").forward(request, response);
		    return;
		}

		if("/add-product".equals(action))
		{
			CategoryDAO categoryDAO = new CategoryDAO();
		    request.setAttribute("categories", categoryDAO.getAllCategories());
			request.getRequestDispatcher("/WEB-INF/admin/addProduct.jsp").forward(request, response);
			return;
		}
		if("/view-orders".equals(action))
		{
			request.getRequestDispatcher("/WEB-INF/admin/viewOrders.jsp").forward(request,response);
			return;
			
		}
		if("/manage-products".equals(action))
		{
			request.getRequestDispatcher("/WEB-INF/admin/ManageProducts.jsp").forward(request,response);
			return;
		}
		if("/edit-product".equals(action))
		{
			request.getRequestDispatcher("/WEB-INF/admin/edit_product.jsp").forward(request, response);
		    return;
		}
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

}

package com.ecommerce.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.util.UUID;


import java.io.IOException;
import java.util.List;

import com.ecommerce.dao.CategoryDAO;
import com.ecommerce.dao.ProductDAO;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;

@WebServlet("/product/*")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024,   // 1MB
	    maxFileSize = 1024 * 1024 * 5,      // 5MB
	    maxRequestSize = 1024 * 1024 * 10   // 10MB
	)
public class ProductServlet extends HttpServlet {
	private ProductDAO productDAO;
	private CategoryDAO categoryDAO;
    @Override
    public void init() {
    	productDAO=new ProductDAO();
    	categoryDAO=new CategoryDAO();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action=request.getPathInfo();
		if(action==null || "/all".equals(action)) {
			List<Product> products=productDAO.getAllProducts();
			request.setAttribute("products", products);
			request.getRequestDispatcher("/user/home.jsp").forward(request, response);
		}
		else if(action.startsWith("/category/")) {
			int categoryId=Integer.parseInt(action.substring(10));
			List<Product> products=productDAO.getProductByCategory(categoryId);
			request.setAttribute("products", products);
			request.getRequestDispatcher("/user/home.jsp").forward(request, response);
		}else if(action.startsWith("/view/")) {
			int productId=Integer.parseInt(action.substring(6));
			Product product=productDAO.getProductById(productId);
			request.setAttribute("product", product);
			request.getRequestDispatcher("/user/product-details.jsp").forward(request, response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(false);
		if(session==null || session.getAttribute("admin")==null) {
			response.sendRedirect(request.getContextPath()+"/admin-login.jsp");
			return;
		}
		String action=request.getPathInfo();
		if("/add".equals(action)) {
			addProduct(request,response);
		}
		else if("/update".equals(action)) {
		    updateProduct(request, response);
		}
		else if("/delete".equals(action)) {
		    deleteProduct(request, response);
		}


	}
	private void addProduct(HttpServletRequest request, HttpServletResponse response)
	        throws IOException, ServletException {

	    String categoryStr = request.getParameter("categoryId");
	  

	    if (categoryStr == null || categoryStr.isEmpty()) {
	        request.setAttribute("error", "Please select a category");
	        request.getRequestDispatcher("/admin/addProduct.jsp").forward(request, response);
	        return;
	    }

	    int categoryId = Integer.parseInt(categoryStr);
	    String productName = request.getParameter("productName");
	    String description = request.getParameter("description");
	    double price = Double.parseDouble(request.getParameter("price"));
	    int quantity = Integer.parseInt(request.getParameter("quantity"));

	    
	    Part imagePart = request.getPart("image");
	    String imagePath = "default-product.jpg";

	    if (imagePart != null && imagePart.getSize() > 0) {
	        String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();

	        
	        String uniqueFileName = UUID.randomUUID() + "_" + fileName;

	     
	        String uploadDir = getServletContext().getRealPath("/uploads/products");
	        File uploadFolder = new File(uploadDir);
	        if (!uploadFolder.exists()) {
	            uploadFolder.mkdirs();
	        }

	       
	        imagePart.write(uploadDir + File.separator + uniqueFileName);

	        imagePath = "uploads/products/" + uniqueFileName;
	    }

	   
	    Product product = new Product();
	    product.setCategoryId(categoryId);
	    product.setProductName(productName);
	    product.setDescription(description);
	    product.setPrice(price);
	    product.setQuantity(quantity);
	    product.setImagePath(imagePath);

	    if (productDAO.addProduct(product)) {
	        response.sendRedirect(request.getContextPath()
	            + "/admin/add-product?success=Product Added Successfully");
	    } else {
	        request.setAttribute("error", "Failed to add Product");
	        request.getRequestDispatcher("/admin/addProduct.jsp").forward(request, response);
	    }
	}
	private void updateProduct(HttpServletRequest request, HttpServletResponse response)
	        throws IOException, ServletException {

	    int productId = Integer.parseInt(request.getParameter("productId"));
	    Product product = productDAO.getProductById(productId);
	    if (product == null) {
	        response.sendRedirect(request.getContextPath() + "/admin/manage-products.jsp");
	        return;
	    }

	    int categoryId = Integer.parseInt(request.getParameter("categoryId"));
	    String productName = request.getParameter("productName");
	    String description = request.getParameter("description");
	    double price = Double.parseDouble(request.getParameter("price"));
	    int quantity = Integer.parseInt(request.getParameter("quantity"));

	    
	    Part imagePart = request.getPart("image");
	    String imagePath = product.getImagePath(); 

	    if (imagePart != null && imagePart.getSize() > 0) {
	        String oldFileName = Paths.get(product.getImagePath()).getFileName().toString();
	        String uploadDir = getServletContext().getRealPath("/uploads/products");
	        File uploadFolder = new File(uploadDir);
	        if (!uploadFolder.exists()) uploadFolder.mkdirs();


	        imagePart.write(uploadDir + File.separator + oldFileName);

	      

	        imagePath = "uploads/products/" + oldFileName;
	    }

	   
	    product.setCategoryId(categoryId);
	    product.setProductName(productName);
	    product.setDescription(description);
	    product.setPrice(price);
	    product.setQuantity(quantity);
	    product.setImagePath(imagePath);
          try
          {
	    if (productDAO.updateProduct(product)) {
	        response.sendRedirect(request.getContextPath() + "/admin/manage-products?success=Product Updated");
	    } else {
	        request.setAttribute("error", "Failed to update Product");
	        request.getRequestDispatcher("/admin/edit-product.jsp?id=" + productId).forward(request, response);
	    }
          }
          catch(Exception e)
          {
        	  e.printStackTrace();
          }
	}
	private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    HttpSession session = request.getSession(false);
	    if (session == null || session.getAttribute("admin") == null) {
	        response.sendRedirect(request.getContextPath() + "/admin-login.jsp");
	        return;
	    }

	    int productId = Integer.parseInt(request.getParameter("productId"));

	    
	    Product product = productDAO.getProductById(productId);
	    if (product != null && product.getImagePath() != null && !product.getImagePath().equals("default-product.jpg")) {
	        String fullPath = getServletContext().getRealPath("/") + product.getImagePath();
	        File file = new File(fullPath);
	        if (file.exists()) {
	            file.delete();
	        }
	    }
          try
          {
	    if (productDAO.deleteProduct(productId))
	    {
	        response.sendRedirect(request.getContextPath() + "/admin/manage-products?success=Product Deleted");
	    } else {
	        response.sendRedirect(request.getContextPath() + "/admin/manage-products?error=Failed to Delete Product");
	    }
          }
          catch(Exception e)
          {
        	  e.printStackTrace();
          }
	}




}
package com.ecommerce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.ecommerce.model.Product;

public class ProductDAO 
{
	 public boolean addProduct(Product product)
	 {
		String sql="Insert into products(category_id,product_name,description,price,quantity,image_path) values(?,?,?,?,?,?) ";
		try(Connection conn=DBConnection.getConnection())
		{
			PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1,product.getCategoryId());
			pst.setString(2,product.getProductName());
			pst.setString(3,product.getDescription());
			pst.setDouble(4,product.getPrice());
			pst.setInt(5,product.getQuantity());
			pst.setString(6,product.getImagePath());
			return pst.executeUpdate()>0;
		}
	 catch(Exception e)
	 {
		 e.printStackTrace();
	 }
	 return false;
}
	 
 public List<Product> getProductByCategory(int categoryId)
	 {
	 	List<Product> products=new ArrayList<>();
	 	String sql="select p.*,c.category_name from products p Join categories c on p.category_id=c.category_id where p.category_id=? and p.quantity>0 ";
	 	try(Connection conn=DBConnection.getConnection())
	 	{
	 		PreparedStatement pst=conn.prepareStatement(sql);
	 		pst.setInt(1,categoryId);
	 		ResultSet rs=pst.executeQuery();
	 		while(rs.next())
	 		{
	 			Product product=extractProductFromResultSet(rs);
	 			products.add(product);
	 		}
	 	}
	 	catch(Exception e)
	 	{
	 		e.printStackTrace();
	 	}
	 	return products;
	 }
	 	
 
 public List<Product> getAllProducts()
		 {
		 	List<Product> products=new ArrayList<>();
		 	String sql="select p.*,c.category_name from products p Join categories c on p.category_id=c.category_id where p.quantity>0 order by p.created_at desc";
		 	try(Connection conn=DBConnection.getConnection())
		 	{
		 		PreparedStatement pst=conn.prepareStatement(sql);
		 		ResultSet rs=pst.executeQuery();
		 		while(rs.next())
		 		{
		 			Product product=extractProductFromResultSet(rs);
		 			products.add(product);
		 		}
		 	}
		 	catch(Exception e)
		 	{
		 		e.printStackTrace();
		 	}
		 	return products;
}
 public Product getProductById(int productId)
 {
 	String sql="select p.*,c.category_name from products p Join categories c on p.category_id=c.category_id where p.product_id=?";
 	try(Connection conn=DBConnection.getConnection())
 	{
 		PreparedStatement pst=conn.prepareStatement(sql);
 		pst.setInt(1,productId);
 		ResultSet rs=pst.executeQuery();
 		if(rs.next())
 		{
 			return extractProductFromResultSet(rs);
 			
 		}
 	}
 	catch(Exception e)
 	{
 		e.printStackTrace();
 	}
 	return null;
}
 
 private Product extractProductFromResultSet(ResultSet rs) throws SQLException
 {
	 Product product=new Product();
	 product.setProductId(rs.getInt("product_id"));
	 product.setCategoryId(rs.getInt("category_id"));
	 product.setProductName(rs.getString("product_name"));
	 product.setDescription(rs.getString("description"));
	 product.setPrice(rs.getDouble("price"));
	 product.setQuantity(rs.getInt("quantity"));
	 product.setImagePath(rs.getString("image_path"));
	 product.setCategoryName(rs.getString("category_name"));
	 return product;
 }
 
 public boolean updateProduct (Product product) throws SQLException
 {
	 String sql="UPDATE products SET category_id=?,product_name=?,description=?,price=?,quantity=?,image_path=? WHERE product_id=?";
	 try(Connection con=DBConnection.getConnection())
	 {
		 PreparedStatement pst=con.prepareStatement(sql);
		 pst.setInt(1, product.getCategoryId());
		 pst.setString(2, product.getProductName());
		 pst.setString(3, product.getDescription());
		 pst.setDouble(4, product.getPrice());
		 pst.setInt(5, product.getQuantity());
		 pst.setString(6, product.getImagePath());
		 pst.setInt(7, product.getProductId());
		 return pst.executeUpdate()>0; 
	 }
	 catch(Exception e)
	 {
		 e.printStackTrace();
	 }
	 return false;
 }
 
 public boolean deleteProduct(int productId) throws SQLException
 {
	 String sql="DELETE FROM products WHERE product_id=?";
	 try(Connection con=DBConnection.getConnection())
	 {
		 PreparedStatement pst=con.prepareStatement(sql);
		 pst.setInt(1,productId);
 		 return pst.executeUpdate()>0;
	 }
	 catch(Exception e)
	 {
		 e.printStackTrace();
	 }
	 return false;
 }
 
 public List<Product> searchProducts(String keyword) throws SQLException
 {
	 List<Product> products=new ArrayList<>();
	 String sql="SELECT p.*,c.category_name"+"FROM products p"+"JOIN category_id=c.category_id"+"WHERE p.quantity>0 AND p.product_name LIKE ?";
	 try(Connection con=DBConnection.getConnection())
	 {
		 PreparedStatement pst=con.prepareStatement(sql);
		 pst.setString(1, "%"+keyword+"%");
		 ResultSet rs=pst.executeQuery();
		 while(rs.next())
		 {
			 products.add(extractProductFromResultSet(rs));
			 
		 } 
	 }
	 catch(Exception e)
	 {
		 e.printStackTrace();
	 }
	 return products;
 }
}
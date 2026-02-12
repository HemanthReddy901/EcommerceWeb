package com.ecommerce.dao;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.ecommerce.model.Category;


public class CategoryDAO
{
 public boolean addCategory(Category category)
 {
	 String sql = "INSERT INTO categories (category_name) VALUES (?)";
	try(Connection conn=DBConnection.getConnection())
	{
		PreparedStatement pst=conn.prepareStatement(sql);
		pst.setString(1, category.getCategoryName());
		return pst.executeUpdate()>0;
	}
 catch(Exception e)
 {
	 e.printStackTrace();
	 return false;
 }
 }



public List<Category> getAllCategories()
{
	List<Category> categories=new ArrayList<>();
	String sql="select * from categories order by category_name";
	try(Connection conn=DBConnection.getConnection())
	{
		PreparedStatement pst=conn.prepareStatement(sql);
		ResultSet rs=pst.executeQuery();
		while(rs.next())
		{
			Category category=new Category();
			category.setCategoryId(rs.getInt("category_id"));
			category.setCategoryName(rs.getString("category_name"));
			categories.add(category);
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return categories;
	
}




}

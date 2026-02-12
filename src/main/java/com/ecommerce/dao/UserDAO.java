package com.ecommerce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ecommerce.model.User;

public class UserDAO 
{
  public boolean registerUser(User user)
  {
	  String sql="Insert into users (email,password,full_name,address,phone) values(?,?,?,?,?)";
	  try(Connection con=DBConnection.getConnection())
	  {
		  PreparedStatement pst=con.prepareStatement(sql);
		  pst.setString(1, user.getEmail());
		  pst.setString(2, user.getPassword());
		  pst.setString(3, user.getFullName());
		  pst.setString(4, user.getAddress());
		  pst.setString(5, user.getPhone());
		  return pst.executeUpdate()>0;
		  
	  }
	  catch(SQLException e) {
		    System.out.println("SQL Error: " + e.getMessage());
		    e.printStackTrace();
		    return false;
		} catch(Exception e) {
		    e.printStackTrace();
		    return false;
		}

	  
  }

  public static User loginUser(String email,String password)
  {
	  String sql = "SELECT * FROM users WHERE email=? AND password=?";
	  try(Connection con=DBConnection.getConnection())
	  {
		  PreparedStatement pst=con.prepareStatement(sql);
		  pst.setString(1, email);
		  pst.setString(2, password);
		  ResultSet rs=pst.executeQuery();
		  if(rs.next())
		  {
			  User user=new User();
			  user.setUserId(rs.getInt("user_id"));
			  user.setEmail(rs.getString("email"));
			  user.setFullName(rs.getString("full_name"));
			  user.setAddress(rs.getString("address"));
			  user.setPhone(rs.getString("phone"));
			  return user;
		  }
	  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
	  }
	  return null;
  }
  public boolean checkEmailExists(String email)
  {
	  String sql="Select count(*) form users where email=?";
	  try(Connection con=DBConnection.getConnection())
	  {
	  PreparedStatement pst=con.prepareStatement(sql);
	  pst.setString(1, email);
	  ResultSet rs=pst.executeQuery();
	  if(rs.next())
	  {
		  return rs.getInt(1)>0;
	  }
	  } catch(Exception e)
	  {
		  e.printStackTrace();
	  }
	  
	 return false; 
  }

  public boolean updateUser(User user) {
	// TODO Auto-generated method stub
	return false;
  }
}

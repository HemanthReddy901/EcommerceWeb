package com.ecommerce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminDAO 
{
	public boolean validateAdmin(String username, String password) {
	    username = username.trim();
	    password = password.trim();

	    String sql = "SELECT * FROM shop_owner WHERE username=? AND password=?";
	    System.out.println("Checking admin: [" + username + "] / [" + password + "]");

	    try (Connection conn = DBConnection.getConnection()) {
	        if (conn == null) {
	            System.out.println("DB connection is null");
	            return false;
	        }

	        PreparedStatement pst = conn.prepareStatement(sql);
	        pst.setString(1, username);
	        pst.setString(2, password);

	        ResultSet rs = pst.executeQuery();
	        boolean found = rs.next();
	        System.out.println("Admin found? " + found);
	        return found;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	

 
 public int getTotalOrders()
 {
	 String sql="Select * from orders";
	 try(Connection conn=DBConnection.getConnection())
		{
		 Statement st=conn.createStatement();
		  ResultSet rs=st.executeQuery(sql);
		  if(rs.next())
		  {
			  return rs.getInt(1);
		  }
		}
	 catch(Exception e)
		{
			e.printStackTrace();
		}
	 return 0;
 }
 public int getDeliveredOrders()
 {
	 String sql="Select count(*) from orders where status='delivered'";
	 try(Connection conn=DBConnection.getConnection())
		{
		 Statement st=conn.createStatement();
		  ResultSet rs=st.executeQuery(sql);
		  if(rs.next())
		  {
			  return rs.getInt(1);
		  }
		}
	 catch(Exception e)
		{
			e.printStackTrace();
		}
	 return 0;
 }
 public double getTotalRevenue()
 {
	 String sql="Select sum(total_amount) from orders where status='delivered'";
	 try(Connection conn=DBConnection.getConnection())
		{
		 Statement st=conn.createStatement();
		  ResultSet rs=st.executeQuery(sql);
		  if(rs.next())
		  {
			  return rs.getDouble(1);
		  }
		}
	 catch(Exception e)
		{
			e.printStackTrace();
		}
	 return 0.0;
 }
}

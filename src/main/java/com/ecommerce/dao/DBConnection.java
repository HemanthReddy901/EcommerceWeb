package com.ecommerce.dao;

import java.sql.*;

public class DBConnection 
{
 private static final String URL="jdbc:mysql://localhost:3306/ecommerce_db";
 private static final String USERNAME="root";
 private static final String PASSWORD="root";
 public static Connection getConnection()
 {
	 Connection con=null;
	 try
	 {
		 Class.forName("com.mysql.cj.jdbc.Driver");
		 con=DriverManager.getConnection(URL,USERNAME,PASSWORD);
	 }
	 catch(ClassNotFoundException | SQLException e)
	 {
		 e.printStackTrace();
	 }
	 return con;
 }
}

/*package com.ecommerce.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/ecommerce_db?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {
        Connection con = null;
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("DB connection successful: " + con);

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Failed to connect to DB.");
            e.printStackTrace();
        }
        return con;
    }

    // Optional: Test connection
    public static void main(String[] args) {
        Connection testCon = getConnection();
        if (testCon != null) {
            System.out.println("Connection is working!");
        } else {
            System.out.println("Connection failed!");
        }
    }
}
*/

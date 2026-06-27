package com.orangehrm.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.orangehrm.base.BaseClass;

public class DBConnection {
	
	
	private static final String DB_URL = "jdbc:mysql://localhost:3307/orangehrm1";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "";
	public static final Logger logger = BaseClass.logger;
	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			logger.info("Database connection established successfully.");
			
			 return connection;
		} catch (ClassNotFoundException e) {
			logger.error("MySQL JDBC Driver not found.");
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error("Failed to connect to the database.");
			e.printStackTrace();
		}
		return connection;
	}
	
   
	
	public static Map<String, String> getEmployeeDetails(String employeeId) throws SQLException
	{
		
		String query = "SELECT emp_firstname, emp_middle_name, emp_lastname FROM hs_hr_employee WHERE employee_id = " +employeeId+ "";
		
		Map<String, String> employeeDetails =  new HashMap<>();
		
		try(Connection conn = getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query))
		{
			if(rs.next())
			{
				employeeDetails.put("first_name", rs.getString("emp_firstname"));
				employeeDetails.put("middle_name", rs.getString("emp_middle_name") != null ? rs.getString("emp_middle_name") : ""); //Handle null middle name
				employeeDetails.put("last_name", rs.getString("emp_lastname"));
				
				logger.info("Query executed successfully, employee details retrieved.");
			}
			return employeeDetails;
	}
		
	}
}

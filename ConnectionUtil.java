package com.nc.dao;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

	private static Connection connection;
	private static final String URL = "jdbc:mysql://localhost:3306/sit";
	private static final String USER = "root";
	private static final String PASS = "mysql";

	private ConnectionUtil() {}
	
	public static Connection getConnection() {
		try {
			if(connection==null || connection.isClosed()) {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(URL,USER,PASS);
			}
		} catch (SQLException e) {
			System.out.println("Problem in getting connetion. "+e.getMessage());
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		return connection;
	}

	
}

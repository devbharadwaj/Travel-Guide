package edu.buffalo.cse636.dbconnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlConnector {

	public Connection connect = null;
	public Statement statement = null;
	public PreparedStatement preparedStatement = null;
	public ResultSet resultSet = null;
	public String userName = "root";
	public String password = "finalfantasy";
	public String query;
	
	public MySqlConnector(String query) {
		this.query = query;
	}
	
	public ResultSet readDataBase() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection("jdbc:mysql://localhost/GoogleMaps?"
							+"user="+userName+"&password="+password);
			statement = connect.createStatement();
			resultSet = statement.executeQuery(query);
			return resultSet;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeResultSet(ResultSet resultSet) throws SQLException {
		while (resultSet.next()) {
			
		}
	}
	
	public void close() {
		try {
			if (resultSet != null)
				resultSet.close();
			if (statement != null)
				statement.close();
			if (connect != null)
				connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

package com.dechengyamjo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String DB_URL = "jdbc:mysql://ec2-18-222-34-185.us-east-2.compute.amazonaws.com:3306/chad?user=dbproject&password=dbproject123";

    // Constructor
    public DatabaseConnector() {
    }

    // Static method to get a reusable database connection
    public static Connection getConnection() throws SQLException {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC Driver not found", e);
        }

        // Return the connection
        return DriverManager.getConnection(DB_URL);
    }

    public int testConnection(int offset) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // SQL query to fetch the date offset
        String query = "SELECT CURDATE() + ?";
        try {
            // Establish the database connection
            connection = getConnection();

            // Prepare the query with the offset
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, offset);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Check and display results
            if (resultSet.next()) {
                String result = resultSet.getString(1);
                System.out.println(offset + " day(s) ahead of the MySQL system clock is: " + result);
            }

            // Return success
            return 0;
        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
            return 1; // Return failure
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

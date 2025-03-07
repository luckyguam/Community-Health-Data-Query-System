package com.dechengyamjo;

import java.sql.*;
import java.util.Scanner;

public class SQLDeveloper {

    public void contributeSQL(Scanner scanner) {
        System.out.println("\n=== Contribute a SQL ===");
        System.out.println("To return to the main menu at any point, type -1.");

        // Only select problems that do not have a SQL contribution
        String query = "SELECT * FROM Problems p " +
                "WHERE NOT EXISTS (SELECT 1 FROM SQL_Contributions c WHERE c.problem_id = p.problem_id)";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Display problems without SQL contributions
            System.out.println("Problems without SQL contributions:");
            boolean hasProblems = false;  // Indicator if there are any problem records to display
            while (resultSet.next()) {
                hasProblems = true;
                System.out.println(resultSet.getInt("problem_id") + ": " + resultSet.getString("description"));
            }

            if (!hasProblems) {
                System.out.println("All problems already have runnable queries.");
                return;  // Exit if no problems need SQL contributions
            }

            System.out.print("\nEnter the ID of the problem you want to contribute a SQL for: ");
            int problemId = scanner.nextInt();
            if (problemId == -1) {
                System.out.println("Returning to the main menu...");
                return;  // Exit the method and go back to the main menu
            }
            scanner.nextLine(); // Consume newline

            System.out.println("\nExample SQL: SELECT * FROM users WHERE id = ?");
            System.out.print("Enter your SQL query: ");
            String sqlQuery = scanner.nextLine();
            if (sqlQuery.equals("-1")) {
                System.out.println("Returning to the main menu...");
                return;
            }

            // Validate SQL
            boolean isValidSQL = validateSQL(sqlQuery);
            if (!isValidSQL) {
                System.out.println("Invalid SQL provided. Please try again.");
                return;
            }

            // Store SQL in the database
            String insertSQL = "INSERT INTO SQL_Contributions (problem_id, sql_statement) VALUES (?, ?)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertSQL)) {
                insertStatement.setInt(1, problemId);
                insertStatement.setString(2, sqlQuery);
                insertStatement.executeUpdate();
                System.out.println("SQL contribution has been added successfully.");
            }

        } catch (SQLException e) {
            System.err.println("Error contributing SQL: " + e.getMessage());
        }
    }

    private boolean validateSQL(String sql) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Count number of placeholders (?)
            int parameterCount = (int) sql.chars().filter(ch -> ch == '?').count();

            // Set dummy values for each placeholder
            for (int i = 1; i <= parameterCount; i++) {
                preparedStatement.setString(i, "DUMMY_VALUE"); // Use a generic dummy value
            }

            // Execute the query to validate it
            preparedStatement.executeQuery();
            return true;

        } catch (SQLException e) {
            System.err.println("SQL validation failed: " + e.getMessage());
            return false;
        }
    }
}
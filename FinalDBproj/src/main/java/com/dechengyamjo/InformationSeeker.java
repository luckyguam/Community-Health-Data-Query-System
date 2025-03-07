package com.dechengyamjo;

import java.sql.*;
import java.util.Scanner;

public class InformationSeeker {

    public void displayRunnableQueries(Scanner scanner) {
        System.out.println("\n=== List of Runnable Queries ===");
        System.out.println("To return to the main menu at any point, type -1.");

        String query = "SELECT p.problem_id, p.description, c.sql_statement " +
                "FROM Problems p " +
                "JOIN SQL_Contributions c ON p.problem_id = c.problem_id";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Display problems with SQL contributions
            System.out.println("Runnable queries:");
            boolean hasRunnableQueries = false;
            while (resultSet.next()) {
                hasRunnableQueries = true;
                System.out.println(resultSet.getInt("problem_id") + ": " + resultSet.getString("description"));
            }

            if (!hasRunnableQueries) {
                System.out.println("No runnable queries found.");
                return; // Exit if no runnable queries are available
            }

            System.out.print("\nEnter the ID of the runnable query to execute: ");
            int problemId = scanner.nextInt();
            if (problemId == -1) {
                System.out.println("Returning to the main menu...");
                return; // Exit the method to return to the main menu
            }
            scanner.nextLine(); // Consume newline

            // Retrieve the SQL query for the selected problem
            String sqlQuery = "SELECT c.sql_statement " +
                    "FROM SQL_Contributions c " +
                    "WHERE c.problem_id = ?";

            try (PreparedStatement queryStatement = connection.prepareStatement(sqlQuery)) {
                queryStatement.setInt(1, problemId);

                try (ResultSet sqlResultSet = queryStatement.executeQuery()) {
                    if (sqlResultSet.next()) {
                        String userSQL = sqlResultSet.getString("sql_statement");

                        // Handle parameters if the SQL has placeholders (e.g., ?)
                        processSQLWithParameters(scanner, connection, userSQL);
                    } else {
                        System.out.println("No runnable query found for the selected problem.");
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving runnable queries: " + e.getMessage());
        }
    }

    private void processSQLWithParameters(Scanner scanner, Connection connection, String sql) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Identify number of SQL parameters (placeholders `?` in the query)
            int parameterCount = (int) sql.chars().filter(ch -> ch == '?').count();

            for (int i = 1; i <= parameterCount; i++) {
                System.out.print("Enter value for parameter " + i + " (type -1 to go back to the main menu): ");
                String paramValue = scanner.nextLine();
                if (paramValue.equals("-1")) {
                    System.out.println("Returning to the main menu...");
                    return; // Exit the method to return to the main menu
                }
                preparedStatement.setString(i, paramValue);
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Display query results
                System.out.println("\nQuery Results:");
                while (resultSet.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.print(metaData.getColumnName(i) + ": " + resultSet.getString(i) + " | ");
                    }
                    System.out.println();
                }
            }

        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
        }
    }
}
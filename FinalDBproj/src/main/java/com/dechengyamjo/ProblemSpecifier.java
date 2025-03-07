package com.dechengyamjo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ProblemSpecifier {

    public void specifyProblem(Scanner scanner) {
        System.out.println("\n=== Specify a Problem ===");
        System.out.println("To return to the main menu at any point, type -1.");

        System.out.print("Enter the description of the problem in English: ");
        String description = scanner.nextLine();
        if (description.equals("-1")) {
            System.out.println("Returning to the main menu...");
            return; // Exit the method to go back to the main menu
        }

        String insertQuery = "INSERT INTO Problems (description) VALUES (?)";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            // Set the description in the SQL
            preparedStatement.setString(1, description);
            preparedStatement.executeUpdate();

            System.out.println("Problem specification has been added successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding problem: " + e.getMessage());
        }
    }
}
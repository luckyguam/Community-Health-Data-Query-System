package com.dechengyamjo;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Test the database connection on startup
        testDatabaseConnection();

        Scanner scanner = new Scanner(System.in);

        ProblemSpecifier problemSpecifier = new ProblemSpecifier();
        SQLDeveloper sqlDeveloper = new SQLDeveloper();
        InformationSeeker informationSeeker = new InformationSeeker();

        while (true) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Specify a problem");
            System.out.println("2. Contribute a SQL");
            System.out.println("3. Display a list of runnable queries");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    problemSpecifier.specifyProblem(scanner);
                    break;
                case 2:
                    sqlDeveloper.contributeSQL(scanner);
                    break;
                case 3:
                    informationSeeker.displayRunnableQueries(scanner);
                    break;
                case 4:
                    System.out.println("Exiting the application. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    /**
     * Test the database connection and verify if it works.
     */
    private static void testDatabaseConnection() {
        System.out.println("Testing database connection...");
        try {
            // Attempt to connect to the database
            DatabaseConnector.getConnection();
            System.out.println("Database connection successful!");
        } catch (Exception e) {
            // Handle errors related to database connection
            System.err.println("Failed to connect to the database: " + e.getMessage());
        }
    }
}
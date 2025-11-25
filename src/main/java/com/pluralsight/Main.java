package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String connectionString = "jdbc:mysql://localhost:3306/northwind";
        String username = "root";
        String password = "Yearup$909";

        Scanner scanner = new Scanner(System.in);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;


        try (Connection conn = DriverManager.getConnection(connectionString, username, password)) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connected!");

            String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock " +
                    "FROM Products";
            String query2 = "SELECT ContactName, CompanyName, City, Country, Phone " +
                    "FROM Customers";
            PreparedStatement psP = conn.prepareStatement(query);
            PreparedStatement psC = conn.prepareStatement(query2);
            ResultSet rsP = psP.executeQuery(query);
            ResultSet rsC = psC.executeQuery(query2);

            int choice = -1;

            while (choice != 0) {
                System.out.println("\nWhat do you want to do?");
                System.out.println("1. Display all Products");
                System.out.println("2. Display all customers");
                System.out.println("0. Exit");
                System.out.print("Your choice: ");

                if (!scanner.hasNextInt()) {
                    System.out.println("Please enter 1, 2, or 0.");
                    scanner.nextLine();
                    continue;
                }

                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> {
                        while (rsP.next()) {
                            int id = rsP.getInt("ProductID");
                            String pName = rsP.getString("ProductName");
                            double price = rsP.getDouble("UnitPrice");
                            int stock = rsP.getInt("UnitsInStock");
                            System.out.printf(" ProductID: %s%n ProductName: %s%n UnitPrice: %s%n UnitInStock: %s%n" +
                                    "%n--------------------------------%n", id, pName, price, stock);
                        }
                    }
                    case 2 -> {

                        while (rsC.next()) {
                            String ctName = rsC.getString("ContactName");
                            String cpName = rsC.getString("CompanyName");
                            String city = rsC.getString("City");
                            String country = rsC.getString("Country");
                            String phone = rsC.getString("Phone");
                            System.out.printf(" ContactName: %s%n CompanyName: %s%n City: %s%n Country: %s Phone: %s%n" +
                                    "%n--------------------------------%n", ctName, cpName, city, country, phone);
                        }
                    }
                    case 3 -> System.out.println("GoodBye");
                    default -> System.out.println("Invalid choice!");
                }
            }

        } catch (SQLException e) {
            System.out.println("An SQLException Occurred: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        scanner.close();
    }
}
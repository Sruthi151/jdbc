package com.example.jdbc_project.jdbcprograms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Program5 {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        Connection con = DriverManager.getConnection(
                "jdbc:sqlserver://localhost:1433;databaseName=keys;trustServerCertificate=true;",
                "sa",
                "<YourStrong@Passw0rd>"
        );

        con.setAutoCommit(false);  

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter first product ID: ");
        int productId1 = sc.nextInt();
        System.out.print("Enter first product name: ");
        sc.nextLine();
        String name1 = sc.nextLine();
        System.out.print("Enter first product price: ");
        double price1 = sc.nextDouble();

        
        System.out.print("Enter second product ID: ");
        int productId2 = sc.nextInt();
        System.out.print("Enter second product name: ");
        sc.nextLine();  
        String name2 = sc.nextLine();
        System.out.print("Enter second product price: ");
        double price2 = sc.nextDouble();

        try {
            String insertUpdateSQL = "MERGE INTO Business AS target " +
                                     "USING (VALUES (?, ?, ?)) AS source (product_id, name, price) " +
                                     "ON target.product_id = source.product_id " +
                                     "WHEN MATCHED THEN UPDATE SET target.name = source.name, target.price = source.price " +
                                     "WHEN NOT MATCHED THEN INSERT (product_id, name, price) VALUES (source.product_id, source.name, source.price);";

            PreparedStatement pstmt1 = con.prepareStatement(insertUpdateSQL);
            pstmt1.setInt(1, productId1);
            pstmt1.setString(2, name1);
            pstmt1.setDouble(3, price1);

            PreparedStatement pstmt2 = con.prepareStatement(insertUpdateSQL);
            pstmt2.setInt(1, productId2);
            pstmt2.setString(2, name2);
            pstmt2.setDouble(3, price2);
            int rowsAffected1 = pstmt1.executeUpdate();
            int rowsAffected2 = pstmt2.executeUpdate();

            if (rowsAffected1 > 0 && rowsAffected2 > 0) {
                con.commit();  
                System.out.println("Both products updated/inserted successfully.");
            } else {
                con.rollback(); 
                System.out.println("Failed to insert/update products. Rollback.");
            }

            pstmt1.close();
            pstmt2.close();
        } catch (SQLException e) {
            try {
                con.rollback();  
                System.out.println("Transaction failed, rolled back.");
            } catch (SQLException ex) {
                System.out.println("Error rolling back transaction: " + ex.getMessage());
            }
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                con.setAutoCommit(true);  
                con.close();
                sc.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}


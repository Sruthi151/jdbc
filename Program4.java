package com.example.jdbc_project.jdbcprograms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Program4 {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        Connection con = DriverManager.getConnection(
                "jdbc:sqlserver://localhost:1433;databaseName=keys;trustServerCertificate=true;",
                "sa",
                "<YourStrong@Passw0rd>"
        );
        
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter product ID: ");
        int productId = sc.nextInt();
        
        System.out.print("Enter quantity sold: ");
        int quantitySold = sc.nextInt();
        
        System.out.print("Enter sale price per unit: ");
        double salePrice = sc.nextDouble();
        
        String selectSQL = "SELECT name, price FROM Business WHERE product_id = ?";
        PreparedStatement selectStmt = con.prepareStatement(selectSQL);
        selectStmt.setInt(1, productId);
        
        ResultSet rs = selectStmt.executeQuery();
        if (rs.next()) {
            String productName = rs.getString("name");
            double productPrice = rs.getDouble("price");
            
            double totalSaleAmount = salePrice * quantitySold;
            
            String insertSQL = "INSERT INTO Business (product_id, name, price) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = con.prepareStatement(insertSQL);
            insertStmt.setInt(1, productId);
            insertStmt.setString(2, productName);
            insertStmt.setDouble(3, totalSaleAmount);
            
            int rowsAffected = insertStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Sale transaction inserted successfully.");
            } else {
                System.out.println("Failed to insert sale transaction.");
            }
            
            insertStmt.close();
        } else {
            System.out.println("Product with ID " + productId + " not found.");
        }
        
        selectStmt.close();
        con.close();
        sc.close();
    }
}




 

        
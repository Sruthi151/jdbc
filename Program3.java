package com.example.jdbc_project.jdbcprograms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Program3 {
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
        
        sc.nextLine(); 
        
        System.out.print("Enter product name: ");
        String productName = sc.nextLine();
        
        System.out.print("Enter product price: ");
        double productPrice = sc.nextDouble();
        
        String insertSQL = "INSERT INTO Business (product_id, name, price) VALUES (?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(insertSQL);
        pstmt.setInt(1, productId);
        pstmt.setString(2, productName);
        pstmt.setDouble(3, productPrice);
        
        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Product inserted successfully.");
        } else {
            System.out.println("Failed to insert product.");
        }
        
        pstmt.close();
        con.close();
        sc.close();
    }
}

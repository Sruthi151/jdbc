package com.example.jdbc_project.jdbcprograms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Program2 {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection con = DriverManager.getConnection(
                "jdbc:sqlserver://localhost:1433;databaseName=keys;trustServerCertificate=true;",
                "sa",
                "<YourStrong@Passw0rd>"
        );
        Statement stmt = con.createStatement();
        String querySQL = "SELECT name, AVG(price) AS average_price " +
                          "FROM Business " +
                          "GROUP BY name";
        ResultSet rs = stmt.executeQuery(querySQL);
        while (rs.next()) {
            String name = rs.getString("name");
            double averagePrice = rs.getDouble("average_price");
            System.out.println("Product Name: " + name);
            System.out.println("Average Price: " + averagePrice);
        }
        rs.close();
        stmt.close();
        con.close();
    }
}

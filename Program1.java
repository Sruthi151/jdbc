package com.example.jdbc_project.jdbcprograms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class Program1 {
    public static void main(String[] args)throws SQLException,ClassNotFoundException{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:sqlserver://localhost:1433;databaseName=keys;trustServerCertificate=true;",
                    "sa",
                    "<YourStrong@Passw0rd>"
            );
            Statement stmt = con.createStatement();
            String TableSQL = "UPDATE Business SET name='deveops' WHERE product_id =45";
            //String InsertTableSQL = "INSERT INTO Business(product_id, name ,price)VALUES(45,'java',645.0),(76,'python',100.0),(90,'sql',456.9)";
            stmt.executeUpdate(TableSQL);
            System.out.println("Updated table ");
            
    }
}


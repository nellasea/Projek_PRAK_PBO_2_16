/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    
    public static Connection getConnection() {
        Connection conn = null;
        
        try {
            // Load driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Koneksi ke database
            String url = "jdbc:mysql://localhost:3306/hostel_db";
            String user = "root";
            String pass = "";
            
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Koneksi berhasil!");
            
        } catch (Exception e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
            e.printStackTrace();
        }
        
        return conn;
    }
}
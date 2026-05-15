/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import config.DatabaseConnection;
import Model.Tamu;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TamuDAO {
    
    public List<Tamu> getAllTamu() {
        List<Tamu> list = new ArrayList<>();
        String query = "SELECT * FROM tamu ORDER BY nama";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Tamu tamu = new Tamu();
                tamu.setId(rs.getInt("id"));
                tamu.setNama(rs.getString("nama"));
                tamu.setNoKtp(rs.getString("no_ktp"));
                tamu.setNoTelepon(rs.getString("no_telepon"));
                tamu.setAlamat(rs.getString("alamat"));
                tamu.setTanggalLahir(rs.getDate("tanggal_lahir"));
                list.add(tamu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean insertTamu(Tamu tamu) {
        String query = "INSERT INTO tamu (nama, no_ktp, no_telepon, alamat, tanggal_lahir) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, tamu.getNama());
            stmt.setString(2, tamu.getNoKtp());
            stmt.setString(3, tamu.getNoTelepon());
            stmt.setString(4, tamu.getAlamat());
            stmt.setDate(5, tamu.getTanggalLahir());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateTamu(Tamu tamu) {
        String query = "UPDATE tamu SET nama=?, no_ktp=?, no_telepon=?, alamat=?, tanggal_lahir=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, tamu.getNama());
            stmt.setString(2, tamu.getNoKtp());
            stmt.setString(3, tamu.getNoTelepon());
            stmt.setString(4, tamu.getAlamat());
            stmt.setDate(5, tamu.getTanggalLahir());
            stmt.setInt(6, tamu.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteTamu(int id) {
        String query = "DELETE FROM tamu WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

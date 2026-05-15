/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import config.DatabaseConnection;
import model.Pembayaran;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PembayaranDAO {
    
    public boolean insertPembayaran(Pembayaran pembayaran) {
        String query = "INSERT INTO pembayaran (id_reservasi, jumlah_bayar, metode_pembayaran, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, pembayaran.getIdReservasi());
            stmt.setBigDecimal(2, pembayaran.getJumlahBayar());
            stmt.setString(3, pembayaran.getMetodePembayaran());
            stmt.setString(4, pembayaran.getStatus());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateStatusPembayaran(int id, String status) {
        String query = "UPDATE pembayaran SET status=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, status);
            stmt.setInt(2, id);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Pembayaran> getPembayaranByReservasi(int idReservasi) {
        List<Pembayaran> list = new ArrayList<>();
        String query = "SELECT * FROM pembayaran WHERE id_reservasi=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, idReservasi);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Pembayaran p = new Pembayaran();
                p.setId(rs.getInt("id"));
                p.setIdReservasi(rs.getInt("id_reservasi"));
                p.setJumlahBayar(rs.getBigDecimal("jumlah_bayar"));
                p.setMetodePembayaran(rs.getString("metode_pembayaran"));
                p.setTanggalBayar(rs.getTimestamp("tanggal_bayar"));
                p.setStatus(rs.getString("status"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
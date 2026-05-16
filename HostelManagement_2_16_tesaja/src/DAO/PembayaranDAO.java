/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Pembayaran;
import config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PembayaranDAO {
    public boolean insert(Pembayaran p) {
        String sql = "INSERT INTO pembayaran (id_reservasi, jumlah_bayar, metode_pembayaran, status) VALUES (?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getIdReservasi());
            stmt.setBigDecimal(2, p.getJumlahBayar());
            stmt.setString(3, p.getMetodePembayaran());
            stmt.setString(4, p.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<Pembayaran> getByReservasi(int idRes) {
        List<Pembayaran> list = new ArrayList<>();
        String sql = "SELECT * FROM pembayaran WHERE id_reservasi=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idRes);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Pembayaran p = new Pembayaran();
                p.setId(rs.getInt("id"));
                p.setIdReservasi(rs.getInt("id_reservasi"));
                p.setJumlahBayar(rs.getBigDecimal("jumlah_bayar"));
                p.setMetodePembayaran(rs.getString("metode_pembayaran"));
                p.setStatus(rs.getString("status"));
                list.add(p);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
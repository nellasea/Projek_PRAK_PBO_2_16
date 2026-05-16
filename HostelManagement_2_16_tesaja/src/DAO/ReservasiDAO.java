/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Reservasi;
import config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservasiDAO {
    public List<Reservasi> getAll() {
        List<Reservasi> list = new ArrayList<>();
        String sql = "SELECT r.*, t.nama as nama_tamu, k.nomor_kamar FROM reservasi r JOIN tamu t ON r.id_tamu=t.id JOIN kamar k ON r.id_kamar=k.id ORDER BY r.tanggal_booking DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Reservasi r = new Reservasi();
                r.setId(rs.getInt("id"));
                r.setIdTamu(rs.getInt("id_tamu"));
                r.setIdKamar(rs.getInt("id_kamar"));
                r.setNamaTamu(rs.getString("nama_tamu"));
                r.setNomorKamar(rs.getString("nomor_kamar"));
                r.setTanggalCheckIn(rs.getDate("tanggal_check_in"));
                r.setTanggalCheckOut(rs.getDate("tanggal_check_out"));
                r.setJumlahMalam(rs.getInt("jumlah_malam"));
                r.setTotalHarga(rs.getBigDecimal("total_harga"));
                r.setStatus(rs.getString("status"));
                list.add(r);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean insert(Reservasi r) {
        String sql = "INSERT INTO reservasi (id_tamu, id_kamar, tanggal_check_in, tanggal_check_out, jumlah_malam, total_harga, status) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, r.getIdTamu());
            stmt.setInt(2, r.getIdKamar());
            stmt.setDate(3, r.getTanggalCheckIn());
            stmt.setDate(4, r.getTanggalCheckOut());
            stmt.setInt(5, r.getJumlahMalam());
            stmt.setBigDecimal(6, r.getTotalHarga());
            stmt.setString(7, r.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE reservasi SET status=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}
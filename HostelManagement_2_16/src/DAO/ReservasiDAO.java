/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import config.DatabaseConnection;
import Model.Reservasi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservasiDAO {
    
    public List<Reservasi> getAllReservasi() {
        List<Reservasi> list = new ArrayList<>();
        String query = "SELECT r.*, t.nama as nama_tamu, k.nomor_kamar " +
                      "FROM reservasi r " +
                      "JOIN tamu t ON r.id_tamu = t.id " +
                      "JOIN kamar k ON r.id_kamar = k.id " +
                      "ORDER BY r.tanggal_booking DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Reservasi reservasi = new Reservasi();
                reservasi.setId(rs.getInt("id"));
                reservasi.setIdTamu(rs.getInt("id_tamu"));
                reservasi.setIdKamar(rs.getInt("id_kamar"));
                reservasi.setNamaTamu(rs.getString("nama_tamu"));
                reservasi.setNomorKamar(rs.getString("nomor_kamar"));
                reservasi.setTanggalCheckIn(rs.getDate("tanggal_check_in"));
                reservasi.setTanggalCheckOut(rs.getDate("tanggal_check_out"));
                reservasi.setJumlahMalam(rs.getInt("jumlah_malam"));
                reservasi.setTotalHarga(rs.getBigDecimal("total_harga"));
                reservasi.setStatus(rs.getString("status"));
                reservasi.setTanggalBooking(rs.getTimestamp("tanggal_booking"));
                list.add(reservasi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean insertReservasi(Reservasi reservasi) {
        String query = "INSERT INTO reservasi (id_tamu, id_kamar, tanggal_check_in, tanggal_check_out, jumlah_malam, total_harga, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, reservasi.getIdTamu());
            stmt.setInt(2, reservasi.getIdKamar());
            stmt.setDate(3, reservasi.getTanggalCheckIn());
            stmt.setDate(4, reservasi.getTanggalCheckOut());
            stmt.setInt(5, reservasi.getJumlahMalam());
            stmt.setBigDecimal(6, reservasi.getTotalHarga());
            stmt.setString(7, reservasi.getStatus());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateStatusReservasi(int id, String status) {
        String query = "UPDATE reservasi SET status=? WHERE id=?";
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
    
    public List<Reservasi> getLaporanPendapatan() {
        List<Reservasi> list = new ArrayList<>();
        String query = "SELECT r.*, t.nama as nama_tamu, k.nomor_kamar, k.harga_per_malam " +
                      "FROM reservasi r " +
                      "JOIN tamu t ON r.id_tamu = t.id " +
                      "JOIN kamar k ON r.id_kamar = k.id " +
                      "WHERE r.status = 'Check-Out' " +
                      "ORDER BY r.tanggal_check_out DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Reservasi reservasi = new Reservasi();
                reservasi.setId(rs.getInt("id"));
                reservasi.setNamaTamu(rs.getString("nama_tamu"));
                reservasi.setNomorKamar(rs.getString("nomor_kamar"));
                reservasi.setTanggalCheckIn(rs.getDate("tanggal_check_in"));
                reservasi.setTanggalCheckOut(rs.getDate("tanggal_check_out"));
                reservasi.setTotalHarga(rs.getBigDecimal("total_harga"));
                list.add(reservasi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}

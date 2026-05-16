/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Kamar;
import config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KamarDAO {
    public List<Kamar> getAll() {
        List<Kamar> list = new ArrayList<>();
        String sql = "SELECT * FROM kamar ORDER BY nomor_kamar";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Kamar k = new Kamar();
                k.setId(rs.getInt("id"));
                k.setNomorKamar(rs.getString("nomor_kamar"));
                k.setTipe(rs.getString("tipe"));
                k.setHargaPerMalam(rs.getBigDecimal("harga_per_malam"));
                k.setStatus(rs.getString("status"));
                k.setFasilitas(rs.getString("fasilitas"));
                list.add(k);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean insert(Kamar k) {
        String sql = "INSERT INTO kamar (nomor_kamar, tipe, harga_per_malam, status, fasilitas) VALUES (?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, k.getNomorKamar());
            stmt.setString(2, k.getTipe());
            stmt.setBigDecimal(3, k.getHargaPerMalam());
            stmt.setString(4, k.getStatus());
            stmt.setString(5, k.getFasilitas());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean update(Kamar k) {
        String sql = "UPDATE kamar SET tipe=?, harga_per_malam=?, status=?, fasilitas=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, k.getTipe());
            stmt.setBigDecimal(2, k.getHargaPerMalam());
            stmt.setString(3, k.getStatus());
            stmt.setString(4, k.getFasilitas());
            stmt.setInt(5, k.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM kamar WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}
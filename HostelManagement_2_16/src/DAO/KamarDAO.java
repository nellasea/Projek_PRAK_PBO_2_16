/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import config.DatabaseConnection;
import model.Kamar;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KamarDAO {
    
    public List<Kamar> getAllKamar() {
        List<Kamar> list = new ArrayList<>();
        String query = "SELECT * FROM kamar ORDER BY nomor_kamar";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Kamar kamar = new Kamar();
                kamar.setId(rs.getInt("id"));
                kamar.setNomorKamar(rs.getString("nomor_kamar"));
                kamar.setTipe(rs.getString("tipe"));
                kamar.setHargaPerMalam(rs.getBigDecimal("harga_per_malam"));
                kamar.setStatus(rs.getString("status"));
                kamar.setFasilitas(rs.getString("fasilitas"));
                list.add(kamar);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean insertKamar(Kamar kamar) {
        String query = "INSERT INTO kamar (nomor_kamar, tipe, harga_per_malam, status, fasilitas) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, kamar.getNomorKamar());
            stmt.setString(2, kamar.getTipe());
            stmt.setBigDecimal(3, kamar.getHargaPerMalam());
            stmt.setString(4, kamar.getStatus());
            stmt.setString(5, kamar.getFasilitas());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateKamar(Kamar kamar) {
        String query = "UPDATE kamar SET tipe=?, harga_per_malam=?, status=?, fasilitas=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, kamar.getTipe());
            stmt.setBigDecimal(2, kamar.getHargaPerMalam());
            stmt.setString(3, kamar.getStatus());
            stmt.setString(4, kamar.getFasilitas());
            stmt.setInt(5, kamar.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteKamar(int id) {
        String query = "DELETE FROM kamar WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Kamar getKamarById(int id) {
        String query = "SELECT * FROM kamar WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Kamar kamar = new Kamar();
                kamar.setId(rs.getInt("id"));
                kamar.setNomorKamar(rs.getString("nomor_kamar"));
                kamar.setTipe(rs.getString("tipe"));
                kamar.setHargaPerMalam(rs.getBigDecimal("harga_per_malam"));
                kamar.setStatus(rs.getString("status"));
                kamar.setFasilitas(rs.getString("fasilitas"));
                return kamar;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

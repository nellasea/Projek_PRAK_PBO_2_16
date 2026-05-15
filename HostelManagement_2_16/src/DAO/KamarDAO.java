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
    
    // Ambil semua kamar
    public List<Kamar> getAllKamar() {
        List<Kamar> list = new ArrayList<>();
        String sql = "SELECT * FROM kamar ORDER BY nomor_kamar";
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
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
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException e) {
            System.out.println("Error get kamar: " + e.getMessage());
        }
        
        return list;
    }
    
    // Ambil kamar berdasarkan ID
public Kamar getKamarById(int id) {
    String sql = "SELECT * FROM kamar WHERE id = ?";
    try {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
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
        
        rs.close();
        stmt.close();
        conn.close();
        
    } catch (SQLException e) {
        System.out.println("Error get kamar by id: " + e.getMessage());
    }
    return null;
}
    
    // Tambah kamar
    public boolean insertKamar(Kamar kamar) {
        String sql = "INSERT INTO kamar (nomor_kamar, tipe, harga_per_malam, status, fasilitas) VALUES (?, ?, ?, ?, ?)";
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, kamar.getNomorKamar());
            stmt.setString(2, kamar.getTipe());
            stmt.setBigDecimal(3, kamar.getHargaPerMalam());
            stmt.setString(4, kamar.getStatus());
            stmt.setString(5, kamar.getFasilitas());
            
            int result = stmt.executeUpdate();
            stmt.close();
            conn.close();
            
            return result > 0;
            
        } catch (SQLException e) {
            System.out.println("Error insert kamar: " + e.getMessage());
            return false;
        }
    }
    
    // Update kamar
    public boolean updateKamar(Kamar kamar) {
        String sql = "UPDATE kamar SET tipe=?, harga_per_malam=?, status=?, fasilitas=? WHERE id=?";
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, kamar.getTipe());
            stmt.setBigDecimal(2, kamar.getHargaPerMalam());
            stmt.setString(3, kamar.getStatus());
            stmt.setString(4, kamar.getFasilitas());
            stmt.setInt(5, kamar.getId());
            
            int result = stmt.executeUpdate();
            stmt.close();
            conn.close();
            
            return result > 0;
            
        } catch (SQLException e) {
            System.out.println("Error update kamar: " + e.getMessage());
            return false;
        }
    }
    
    // Hapus kamar - INI METHOD YANG DIPANGGIL!
    public boolean deleteKamar(int id) {
        String sql = "DELETE FROM kamar WHERE id=?";
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setInt(1, id);
            
            int result = stmt.executeUpdate();
            stmt.close();
            conn.close();
            
            return result > 0;
            
        } catch (SQLException e) {
            System.out.println("Error delete kamar: " + e.getMessage());
            return false;
        }
    }
}
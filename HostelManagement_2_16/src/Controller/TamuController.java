/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.TamuDAO;
import Model.Tamu;
import java.util.List;

public class TamuController {
    
    private TamuDAO dao = new TamuDAO();
    
    // Ambil semua data tamu
    public List<Tamu> getAllTamu() {
        return dao.getAllTamu();
    }
    
    // Tambah tamu baru
    public boolean insertTamu(Tamu tamu) {
        return dao.insertTamu(tamu);
    }
    
    // Edit tamu
    public boolean updateTamu(Tamu tamu) {
        return dao.updateTamu(tamu);
    }
    
    // Hapus tamu 
    public boolean deleteTamu(int id) {
        return dao.deleteTamu(id);
    }
}
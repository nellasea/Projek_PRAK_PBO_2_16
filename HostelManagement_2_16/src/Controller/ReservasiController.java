/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.ReservasiDAO;
import Model.Reservasi;
import java.util.List;

public class ReservasiController {
    
    private ReservasiDAO dao = new ReservasiDAO();
    
    public List<Reservasi> getAllReservasi() {
        return dao.getAllReservasi();
    }
    
    public boolean addReservasi(Reservasi reservasi) {
        return dao.insertReservasi(reservasi);
    }
    
    public boolean updateStatus(int id, String status) {
        return dao.updateStatusReservasi(id, status);
    }
    
    public List<Reservasi> getLaporanPendapatan() {
        return dao.getLaporanPendapatan();
    }
}

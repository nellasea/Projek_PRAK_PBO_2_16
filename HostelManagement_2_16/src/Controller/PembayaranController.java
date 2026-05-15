/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import dao.PembayaranDAO;
import model.Pembayaran;
import java.util.List;

public class PembayaranController {
    private PembayaranDAO dao = new PembayaranDAO();

    public boolean insertPembayaran(Pembayaran p) {
        return dao.insertPembayaran(p);
    }

    public boolean updateStatus(int id, String status) {
        return dao.updateStatusPembayaran(id, status);
    }

    public List<Pembayaran> getPembayaranByReservasi(int idReservasi) {
        return dao.getPembayaranByReservasi(idReservasi);
    }
}
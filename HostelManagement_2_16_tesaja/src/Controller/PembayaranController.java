/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import DAO.PembayaranDAO;
import Model.Pembayaran;
import java.util.List;
public class PembayaranController {
    private PembayaranDAO dao = new PembayaranDAO();
    public boolean add(Pembayaran p) { return dao.insert(p); }
    public List<Pembayaran> getByReservasi(int id) { return dao.getByReservasi(id); }
}
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
    public List<Reservasi> getAll() { return dao.getAll(); }
    public boolean add(Reservasi r) { return dao.insert(r); }
    public boolean updateStatus(int id, String status) { return dao.updateStatus(id, status); }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import DAO.KamarDAO;
import Model.Kamar;
import java.util.List;
public class KamarController {
    private KamarDAO dao = new KamarDAO();
    public List<Kamar> getAll() { return dao.getAll(); }
    public boolean add(Kamar k) { return dao.insert(k); }
    public boolean edit(Kamar k) { return dao.update(k); }
    public boolean delete(int id) { return dao.delete(id); }
}
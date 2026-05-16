/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import config.DatabaseConnection;

public class LaporanView extends JFrame {
    private JTable tbl = new JTable();
    private DefaultTableModel model = new DefaultTableModel(new String[]{"ID","Tamu","Kamar","In","Out","Total","Metode","Status"},0);

    public LaporanView() {
        setTitle("Laporan Pendapatan");
        setSize(900,500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10));
        tbl.setModel(model); add(new JScrollPane(tbl), BorderLayout.CENTER);
        
        JButton btnLoad = new JButton("Load Laporan");
        JPanel top = new JPanel(); top.add(btnLoad);
        add(top, BorderLayout.NORTH);

        btnLoad.addActionListener(e -> load());
        load();
    }

    private void load() {
        model.setRowCount(0);
        try(Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM laporan_pendapatan")) {
            while(rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"), rs.getString("nama_tamu"), rs.getString("nomor_kamar"),
                    rs.getDate("tanggal_check_in"), rs.getDate("tanggal_check_out"),
                    rs.getBigDecimal("total_harga"), rs.getString("metode_pembayaran"), rs.getString("status_pembayaran")
                });
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import controller.LaporanController;
import model.Reservasi;
import util.NumberHelper;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LaporanView extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JLabel lblTotal;
    private LaporanController ctrl;

    public LaporanView() {
        ctrl = new LaporanController();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = {"ID", "Nama Tamu", "No Kamar", "Check-In", "Check-Out", "Total Bayar"};
        model = new DefaultTableModel(cols, 0) { public boolean isCellEditable(int r, int c) { return false; } };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        JButton btnLoad = new JButton("Load Laporan Check-Out");
        lblTotal = new JLabel("Total Pendapatan: Rp 0", SwingConstants.RIGHT);
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        
        bottom.add(btnLoad, BorderLayout.NORTH);
        bottom.add(lblTotal, BorderLayout.SOUTH);
        add(bottom, BorderLayout.SOUTH);

        btnLoad.addActionListener(e -> {
            model.setRowCount(0);
            double sum = 0;
            List<Reservasi> list = ctrl.getLaporanPendapatan();
            for(Reservasi r : list) {
                model.addRow(new Object[]{r.getId(), r.getNamaTamu(), r.getNomorKamar(),
                    r.getTanggalCheckIn(), r.getTanggalCheckOut(), NumberHelper.formatRupiah(r.getTotalHarga().doubleValue())});
                sum += r.getTotalHarga().doubleValue();
            }
            lblTotal.setText("Total Pendapatan: " + NumberHelper.formatRupiah(sum));
        });
    }
}
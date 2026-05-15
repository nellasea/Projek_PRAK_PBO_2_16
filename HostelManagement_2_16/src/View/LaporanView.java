/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Controller.LaporanController;  // FIXED: uppercase Controller
import Model.Reservasi;               // FIXED: uppercase Model
import Util.NumberHelper;             // FIXED: uppercase Util
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
        
        // FIXED: Added panel for filter bulanan
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> cbBulan = new JComboBox<>(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"});
        JComboBox<String> cbTahun = new JComboBox<>(new String[]{"2023", "2024", "2025"});
        JButton btnFilter = new JButton("Filter Bulanan");
        JButton btnLoad = new JButton("Load Semua Check-Out");
        filterPanel.add(new JLabel("Bulan:")); filterPanel.add(cbBulan);
        filterPanel.add(new JLabel("Tahun:")); filterPanel.add(cbTahun);
        filterPanel.add(btnFilter);
        filterPanel.add(btnLoad);
        
        lblTotal = new JLabel("Total Pendapatan: Rp 0", SwingConstants.RIGHT);
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        
        bottom.add(filterPanel, BorderLayout.NORTH);
        bottom.add(lblTotal, BorderLayout.SOUTH);
        add(bottom, BorderLayout.SOUTH);

        btnLoad.addActionListener(e -> loadLaporan(ctrl.getLaporanPendapatan()));
        btnFilter.addActionListener(e -> {
            String bulan = (String) cbBulan.getSelectedItem();
            String tahun = (String) cbTahun.getSelectedItem();
            loadLaporan(ctrl.getLaporanBulanan(bulan, tahun));
        });
    }
    
    private void loadLaporan(List<Reservasi> list) {
        model.setRowCount(0);
        double sum = 0;
        for(Reservasi r : list) {
            model.addRow(new Object[]{r.getId(), r.getNamaTamu(), r.getNomorKamar(),
                r.getTanggalCheckIn(), r.getTanggalCheckOut(), NumberHelper.formatRupiah(r.getTotalHarga().doubleValue())});
            sum += r.getTotalHarga().doubleValue();
        }
        lblTotal.setText("Total Pendapatan: " + NumberHelper.formatRupiah(sum));
    }
}
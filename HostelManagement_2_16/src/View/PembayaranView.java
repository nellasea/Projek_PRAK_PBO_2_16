/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import controller.PembayaranController;
import model.Pembayaran;
import util.NumberHelper;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class PembayaranView extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtId, txtIdReservasi, txtJumlah;
    private JComboBox<String> cbMetode, cbStatus;
    private PembayaranController ctrl;

    public PembayaranView() {
        ctrl = new PembayaranController();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = {"ID", "ID Reservasi", "Jumlah", "Metode", "Tanggal", "Status"};
        model = new DefaultTableModel(cols, 0) { 
            public boolean isCellEditable(int r, int c) { return false; } 
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(2, 5, 8, 8));
        form.add(new JLabel("ID Pembayaran:")); txtId = new JTextField(); txtId.setEditable(false); form.add(txtId);
        form.add(new JLabel("ID Reservasi:")); txtIdReservasi = new JTextField(); form.add(txtIdReservasi);
        form.add(new JLabel("Jumlah:")); txtJumlah = new JTextField(); form.add(txtJumlah);
        form.add(new JLabel("Metode:")); cbMetode = new JComboBox<>(new String[]{"Cash", "Transfer", "Kartu Kredit"}); form.add(cbMetode);
        form.add(new JLabel("Status:")); cbStatus = new JComboBox<>(new String[]{"Lunas", "Belum Lunas"}); form.add(cbStatus);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnBayar = new JButton("Proses Pembayaran");
        JButton btnRefresh = new JButton("Refresh");
        btns.add(btnBayar); btns.add(btnRefresh);

        add(form, BorderLayout.NORTH);
        add(btns, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> loadTable());
        btnBayar.addActionListener(e -> prosesPembayaran());

        loadTable();
    }

    private void loadTable() {
        model.setRowCount(0);
        // Implementasi load data pembayaran
        // (Butuh PembayaranDAO terlebih dahulu)
    }

    private void prosesPembayaran() {
        if (txtIdReservasi.getText().isEmpty() || txtJumlah.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lengkapi data pembayaran!");
            return;
        }
        
        Pembayaran p = new Pembayaran();
        p.setIdReservasi(Integer.parseInt(txtIdReservasi.getText()));
        p.setJumlahBayar(new BigDecimal(txtJumlah.getText()));
        p.setMetodePembayaran(cbMetode.getSelectedItem().toString());
        p.setStatus(cbStatus.getSelectedItem().toString());
        
        if (ctrl.insertPembayaran(p)) {
            JOptionPane.showMessageDialog(this, "Pembayaran berhasil!");
            loadTable();
        }
    }
}

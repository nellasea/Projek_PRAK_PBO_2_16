/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import controller.ReservasiController;
import model.Reservasi;
import util.DateHelper;
import util.NumberHelper;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class ReservasiView extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtId, txtIdTamu, txtIdKamar, txtTotal;
    private JDateChooser dcCheckIn, dcCheckOut; // Gunakan com.toedter.calendar.JDateChooser atau JTextField format yyyy-MM-dd
    private JComboBox<String> cbStatus;
    private ReservasiController ctrl;

    public ReservasiView() {
        ctrl = new ReservasiController();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = {"ID", "ID Tamu", "ID Kamar", "Check-In", "Check-Out", "Malam", "Total", "Status"};
        model = new DefaultTableModel(cols, 0) { public boolean isCellEditable(int r, int c) { return false; } };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(3, 5, 8, 8));
        form.add(new JLabel("ID Res:")); txtId = new JTextField(); form.add(txtId);
        form.add(new JLabel("ID Tamu:")); txtIdTamu = new JTextField(); form.add(txtIdTamu);
        form.add(new JLabel("ID Kamar:")); txtIdKamar = new JTextField(); form.add(txtIdKamar);
        form.add(new JLabel("Check-In (yyyy-MM-dd):")); JTextField txtIn = new JTextField(); form.add(txtIn);
        form.add(new JLabel("Check-Out (yyyy-MM-dd):")); JTextField txtOut = new JTextField(); form.add(txtOut);
        form.add(new JLabel("Status:")); cbStatus = new JComboBox<>(new String[]{"Booking", "Check-In", "Check-Out", "Cancelled"}); form.add(cbStatus);
        form.add(new JLabel("Total Harga:")); txtTotal = new JTextField(); txtTotal.setEditable(false); form.add(txtTotal);
        
        // Auto calculate total when dates change
        txtOut.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                java.sql.Date in = DateHelper.parseToDate(txtIn.getText());
                java.sql.Date out = DateHelper.parseToDate(txtOut.getText());
                if(in != null && out != null) {
                    int days = DateHelper.calculateDaysBetween(in, out);
                    // Asumsi harga kamar fixed 150000 untuk demo. Di app nyata, ambil dari DB based on ID Kamar
                    txtTotal.setText(String.valueOf(days * 150000));
                }
            }
        });

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAdd = new JButton("Booking");
        JButton btnStatus = new JButton("Update Status");
        JButton btnRefresh = new JButton("Refresh");
        btns.add(btnAdd); btns.add(btnStatus); btns.add(btnRefresh);

        add(form, BorderLayout.NORTH);
        add(btns, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> loadTable());
        btnAdd.addActionListener(e -> {
            Reservasi r = new Reservasi();
            r.setIdTamu(Integer.parseInt(txtIdTamu.getText()));
            r.setIdKamar(Integer.parseInt(txtIdKamar.getText()));
            r.setTanggalCheckIn(DateHelper.parseToDate(txtIn.getText()));
            r.setTanggalCheckOut(DateHelper.parseToDate(txtOut.getText()));
            r.setJumlahMalam(DateHelper.calculateDaysBetween(r.getTanggalCheckIn(), r.getTanggalCheckOut()));
            r.setTotalHarga(new BigDecimal(txtTotal.getText()));
            r.setStatus(cbStatus.getSelectedItem().toString());
            if(ctrl.insertReservasi(r)) { JOptionPane.showMessageDialog(this, "Booking Berhasil!"); loadTable(); }
        });
        btnStatus.addActionListener(e -> {
            if(txtId.getText().isEmpty()) return;
            if(ctrl.updateStatus(Integer.parseInt(txtId.getText()), cbStatus.getSelectedItem().toString())) {
                JOptionPane.showMessageDialog(this, "Status Updated!"); loadTable();
            }
        });

        loadTable();
    }

    private void loadTable() {
        model.setRowCount(0);
        for(Reservasi r : ctrl.getAllReservasi()) {
            model.addRow(new Object[]{r.getId(), r.getIdTamu(), r.getIdKamar(), 
                DateHelper.formatToIndonesian(r.getTanggalCheckIn()), DateHelper.formatToIndonesian(r.getTanggalCheckOut()),
                r.getJumlahMalam(), NumberHelper.formatRupiah(r.getTotalHarga().doubleValue()), r.getStatus()});
        }
    }
}
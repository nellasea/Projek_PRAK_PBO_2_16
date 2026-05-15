/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Controller.PembayaranController;
import Model.Pembayaran;
import Util.NumberHelper;
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
    private JButton btnBayar, btnRefresh, btnUpdateStatus;

    public PembayaranView() {
        ctrl = new PembayaranController();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = {"ID", "ID Reservasi", "Jumlah", "Metode", "Tanggal", "Status"};
        model = new DefaultTableModel(cols, 0) { 
            public boolean isCellEditable(int r, int c) { return false; } 
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(2, 5, 8, 8));
        form.add(new JLabel("ID Pembayaran:")); txtId = new JTextField(); txtId.setEditable(false); form.add(txtId);
        form.add(new JLabel("ID Reservasi:")); txtIdReservasi = new JTextField(); form.add(txtIdReservasi);
        form.add(new JLabel("Jumlah:")); txtJumlah = new JTextField(); form.add(txtJumlah);
        form.add(new JLabel("Metode:")); cbMetode = new JComboBox<>(new String[]{"Cash", "Transfer", "Kartu Kredit"}); form.add(cbMetode);
        form.add(new JLabel("Status:")); cbStatus = new JComboBox<>(new String[]{"Lunas", "Pending", "Gagal"}); form.add(cbStatus);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnBayar = new JButton("Proses Pembayaran");
        btnUpdateStatus = new JButton("Update Status");
        btnRefresh = new JButton("Refresh");
        btns.add(btnBayar); btns.add(btnUpdateStatus); btns.add(btnRefresh);

        add(form, BorderLayout.NORTH);
        add(btns, BorderLayout.SOUTH);

        // Events
        btnRefresh.addActionListener(e -> loadTable());
        btnBayar.addActionListener(e -> prosesPembayaran());
        btnUpdateStatus.addActionListener(e -> updateStatus());
        
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if(row >= 0) {
                    txtId.setText(model.getValueAt(row, 0).toString());
                    txtIdReservasi.setText(model.getValueAt(row, 1).toString());
                    txtJumlah.setText(model.getValueAt(row, 2).toString().replace("Rp", "").replace(".", "").trim());
                    cbMetode.setSelectedItem(model.getValueAt(row, 3).toString());
                    cbStatus.setSelectedItem(model.getValueAt(row, 5).toString());
                }
            }
        });

        loadTable();
    }

    private void loadTable() {
        model.setRowCount(0);
        if(!txtIdReservasi.getText().isEmpty()) {
            try {
                int idReservasi = Integer.parseInt(txtIdReservasi.getText());
                List<Pembayaran> list = ctrl.getPembayaranByReservasi(idReservasi);
                for(Pembayaran p : list) {
                    model.addRow(new Object[]{
                        p.getId(), 
                        p.getIdReservasi(), 
                        NumberHelper.formatRupiah(p.getJumlahBayar().doubleValue()),
                        p.getMetodePembayaran(), 
                        p.getTanggalBayar(), 
                        p.getStatus()
                    });
                }
            } catch(Exception ex) {
                // If no specific reservation, load all? or just ignore
            }
        }
    }

    private void prosesPembayaran() {
        if (txtIdReservasi.getText().isEmpty() || txtJumlah.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lengkapi data pembayaran!");
            return;
        }
        
        try {
            Pembayaran p = new Pembayaran();
            p.setIdReservasi(Integer.parseInt(txtIdReservasi.getText()));
            p.setJumlahBayar(new BigDecimal(txtJumlah.getText()));
            p.setMetodePembayaran(cbMetode.getSelectedItem().toString());
            p.setStatus(cbStatus.getSelectedItem().toString());
            
            if (ctrl.insertPembayaran(p)) {
                JOptionPane.showMessageDialog(this, "Pembayaran berhasil!");
                loadTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Pembayaran gagal!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Format ID Reservasi atau Jumlah tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateStatus() {
        if(txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih pembayaran terlebih dahulu!");
            return;
        }
        
        if(ctrl.updateStatus(Integer.parseInt(txtId.getText()), cbStatus.getSelectedItem().toString())) {
            JOptionPane.showMessageDialog(this, "Status pembayaran berhasil diupdate!");
            loadTable();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal update status!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearForm() {
        txtId.setText("");
        txtIdReservasi.setText("");
        txtJumlah.setText("");
        cbMetode.setSelectedIndex(0);
        cbStatus.setSelectedIndex(0);
    }
}